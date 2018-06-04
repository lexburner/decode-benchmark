package moe.cnkirito.benchmark.dubbo.transport;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import moe.cnkirito.benchmark.dubbo.codec.DubboRpcBatchDecoder;
import moe.cnkirito.benchmark.dubbo.codec.DubboRpcEncoder;

/**
 * @author 徐靖峰
 * Date 2018-06-04
 */
public class BatchDecodeChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new DubboRpcEncoder())
                .addLast(new DubboRpcBatchDecoder())
                .addLast(new ConsumerAgentBatchHandler());
    }
}
