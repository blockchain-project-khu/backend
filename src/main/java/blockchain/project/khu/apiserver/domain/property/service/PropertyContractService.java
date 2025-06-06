package blockchain.project.khu.apiserver.domain.property.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.RawTransactionManager;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyContractService {

    private final Web3j web3j;
    private final Credentials credentials;
    private final String contractAddress;

    public void registerProperty(Long propertyId, BigInteger price, BigInteger duration, BigInteger rent) throws Exception {
        /* 매물 등록하는 함수 */
        Function function = new Function(
                "registerProperty",
                Arrays.asList(
                        new Uint256(propertyId),
                        new Uint256(price),
                        new Uint256(duration),
                        new Uint256(rent)
                ),
                Collections.emptyList()
        );
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransactionManager txManager = new RawTransactionManager(web3j, credentials, 1001L); // 1001: KAIA 체인ID

        // 4. 가스 가격/한도 명시적으로 지정
        BigInteger gasPrice = new BigInteger("210000000000"); // 210 gwei = 210,000,000,000 wei
        BigInteger gasLimit = new BigInteger("300000");      // 충분한 가스 한도

        // 5. 트랜잭션 전송
        EthSendTransaction response = txManager.sendTransaction(
                gasPrice,
                gasLimit,
                contractAddress,
                encodedFunction,
                BigInteger.ZERO // value
        );

        if (response.hasError()) {
            System.out.println("Tx Error: " + response.getError().getMessage());
            return;
        }

        String txHash = response.getTransactionHash();
        System.out.println("registerProperty tx: " + txHash);

        // 트랜잭션 영수증 대기 및 결과 확인
        TransactionReceipt receipt = waitForReceipt(txHash);
        if (receipt != null && receipt.isStatusOK()) {
            System.out.println("registerProperty 성공! 블록넘버: " + receipt.getBlockNumber());
        } else {
            System.out.println("registerProperty 실패 또는 revert 발생!");
        }

        String myAddress = credentials.getAddress();
        String owner = "0xEDEa0c334d1F2cA78369C34412D8cB410d8672AD"; // PropertyManager 컨트랙트의 owner() 함수 호출로 확인
        System.out.println("내 주소: " + myAddress);
        System.out.println("컨트랙트 owner: " + owner);
    }

    // 트랜잭션 영수증을 기다리는 함수
    public TransactionReceipt waitForReceipt(String txHash) throws Exception {
        int attempts = 40;
        int sleepMillis = 1500;
        for (int i = 0; i < attempts; i++) {
            EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(txHash).send();
            if (receipt.getTransactionReceipt().isPresent()) {
                return receipt.getTransactionReceipt().get();
            }
            Thread.sleep(sleepMillis);
        }
        return null;
    }

    // propertyCount 값을 읽는 함수
    public BigInteger getPropertyCount() throws Exception {
        Function function = new Function(
                "propertyCount",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {})
        );
        String encodedFunction = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(
                credentials.getAddress(), contractAddress, encodedFunction
        );
        EthCall response = web3j.ethCall(transaction, org.web3j.protocol.core.DefaultBlockParameterName.LATEST).send();
        List<Type> output = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
        if (!output.isEmpty()) {
            return (BigInteger) output.get(0).getValue();
        }
        return BigInteger.valueOf(-1);
    }

    // 특정 propertyId의 share price를 조회하는 함수
    public BigInteger getSharePrice(BigInteger propertyId) throws Exception {
        Function function = new Function(
                "getSharePrice",
                Collections.singletonList(new Uint256(propertyId)),
                Collections.singletonList(new TypeReference<Uint256>() {})
        );
        String encodedFunction = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(
                credentials.getAddress(), contractAddress, encodedFunction
        );
        EthCall response = web3j.ethCall(transaction, org.web3j.protocol.core.DefaultBlockParameterName.LATEST).send();
        List<Type> output = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
        if (!output.isEmpty()) {
            return (BigInteger) output.get(0).getValue();
        }
        return BigInteger.valueOf(-1);
    }
}
