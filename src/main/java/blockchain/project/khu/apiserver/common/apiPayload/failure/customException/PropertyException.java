package blockchain.project.khu.apiserver.common.apiPayload.failure.customException;

import blockchain.project.khu.apiserver.common.apiPayload.BaseApiResponse;

public class PropertyException{

    public static class PropertyNotFound extends RuntimeException{}
    public static class PropertyInvalidInputException extends RuntimeException{}
    public static class PropertyDuplicateException extends RuntimeException{}
}
