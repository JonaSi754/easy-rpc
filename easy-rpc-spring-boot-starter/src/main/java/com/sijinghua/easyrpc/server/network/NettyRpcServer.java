package com.sijinghua.easyrpc.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyRpcServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyRpcServer.class);

    private int port;

    private RequestHandler requestHandler;

    private Channel channel;

    public NettyRpcServer(int port, RequestHandler requestHandler) {
        this.port = port;
        this.requestHandler = requestHandler;
    }

    @Override
    public void start() {
        // Create two thread group
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // Create start object of server
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    // set two thread group
                    .group(bossGroup, workerGroup)
                    // set pipe type of server
                    .channel(NioServerSocketChannel.class)
                    // receive connection (bossGroup), set the queue size
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // children pipe, workerGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // set custom handler for pipeline
                        @Override
                        public void initChannel(SocketChannel channel) {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new ChannelRequestHandler());
                        }
                    });

            // Bind with port, start service
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            logger.info("[easy-rpc]Rpc server started on port: {}", port);
            channel = channelFuture.channel();
            // Listen to the close pipeline
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("server error.", e);
        } finally {
            // release EventLoopGroup resource
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        channel.close();
    }

    private class ChannelRequestHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            logger.info("channel active: {}", ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info("Server receive a message: {}", msg);
            final ByteBuf msgBuf = (ByteBuf) msg;
            final byte[] reqBytes = new byte[msgBuf.readableBytes()];
            msgBuf.readBytes(reqBytes);
            // Call request handler to handle request from client
            final byte[] respBytes = requestHandler.handleRequest(reqBytes);
            logger.info("Send response message: {}", respBytes);
            final ByteBuf respBuf = Unpooled.buffer(respBytes.length);
            respBuf.writeBytes(respBytes);
            ctx.writeAndFlush(respBuf);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            logger.error("Caught Exception.", cause);
            ctx.close();
        }
    }
}
