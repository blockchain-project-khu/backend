package blockchain.project.khu.apiserver.common.apiPayload.failure;

import blockchain.project.khu.apiserver.common.apiPayload.BaseApiResponse;
import lombok.Getter;

@Getter
public class ExceptionApiResponse extends BaseApiResponse {

    public ExceptionApiResponse(Boolean isSuccess, String code, String message) {
        super(isSuccess, code, message);
    }

}