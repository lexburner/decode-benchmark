package moe.cnkirito.benchmark.dubbo.transport;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import moe.cnkirito.benchmark.dubbo.model.DubboRpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author 徐靖峰
 * Date 2018-05-17
 */
public class ConsumerAgentHandler extends SimpleChannelInboundHandler<DubboRpcResponse> {

    private Logger logger = LoggerFactory.getLogger(ConsumerAgentHandler.class);

    private ResponseEntity ok = new ResponseEntity("OK", HttpStatus.OK);
    private ResponseEntity error = new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DubboRpcResponse dubboRpcResponse) {
        precessResponse(dubboRpcResponse);
    }

    private void precessResponse(DubboRpcResponse dubboRpcResponse) {
        DeferredResult<ResponseEntity> deferredResult = DeferredResultHolder.responseHolder.get(dubboRpcResponse.getRequestId());
        String requestParam = DeferredResultHolder.requestParamHolder.get(dubboRpcResponse.getRequestId());
        if(deferredResult!=null){
            if(String.valueOf(requestParam.hashCode()).equals(new String(dubboRpcResponse.getBytes()))){
                deferredResult.setResult(ok);
            }else{
                deferredResult.setResult(error);
            }
            DeferredResultHolder.responseHolder.remove(dubboRpcResponse.getRequestId());
            DeferredResultHolder.requestParamHolder.remove(dubboRpcResponse.getRequestId());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.channel().close();
    }

}
