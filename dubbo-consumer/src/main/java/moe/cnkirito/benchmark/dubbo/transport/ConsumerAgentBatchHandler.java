package moe.cnkirito.benchmark.dubbo.transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import moe.cnkirito.benchmark.dubbo.model.DubboRpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

public class ConsumerAgentBatchHandler extends SimpleChannelInboundHandler<Object> {

    private Logger logger = LoggerFactory.getLogger(ConsumerAgentBatchHandler.class);

    private ResponseEntity ok = new ResponseEntity("OK", HttpStatus.OK);
    private ResponseEntity error = new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof DubboRpcResponse){
            DubboRpcResponse dubboRpcResponse = (DubboRpcResponse) msg;
            precessResponse(dubboRpcResponse);
        }else if(msg instanceof List){
            for (DubboRpcResponse dubboRpcResponse : (List<DubboRpcResponse>) msg) {
                precessResponse(dubboRpcResponse);
            }
        }
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
        logger.error("consumerAgentHandler出现异常", cause);
        ctx.channel().close();
    }

}
