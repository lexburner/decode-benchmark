package moe.cnkirito.benchmark.dubbo.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import moe.cnkirito.benchmark.dubbo.codec.DubboRpcBatchDecoder;
import moe.cnkirito.benchmark.dubbo.codec.DubboRpcDecoder;
import moe.cnkirito.benchmark.dubbo.codec.DubboRpcEncoder;
import moe.cnkirito.benchmark.dubbo.common.JsonUtils;
import moe.cnkirito.benchmark.dubbo.model.DubboRpcRequest;
import moe.cnkirito.benchmark.dubbo.model.RpcInvocation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;


public class NormalClient {

    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    public Channel connect(final String host,final int port){
        Bootstrap b = new Bootstrap();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new SingleDecodeChannelInitializer());
//                .handler(new BatchDecodeChannelInitializer());
        ChannelFuture f = b.connect(host, port);
        return this.channel = f.channel();
    }

    public DeferredResult<ResponseEntity> call(String param){
        DubboRpcRequest request = prepareRequest(param);
        DeferredResult<ResponseEntity> result = new DeferredResult<>();
        DeferredResultHolder.responseHolder.put(request.getId(), result);//存放结果
        DeferredResultHolder.requestParamHolder.put(request.getId(), param);//存放请求参数，用于比对hash计算结果
        channel.writeAndFlush(request);
        return result;
    }

    DubboRpcRequest prepareRequest(String param){
        RpcInvocation invocation = new RpcInvocation();
        invocation.setMethodName("hash");
        invocation.setAttachment("path", "moe.cnkirito.benchmark.IHelloService");
        invocation.setParameterTypes("Ljava/lang/String;");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
        try {
            JsonUtils.writeObject(param, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        invocation.setArguments(out.toByteArray());
        DubboRpcRequest dubboRpcRequest = new DubboRpcRequest();
        dubboRpcRequest.setVersion("2.0.0");
        dubboRpcRequest.setTwoWay(true);
        dubboRpcRequest.setData(invocation);
        return dubboRpcRequest;
    }
}
