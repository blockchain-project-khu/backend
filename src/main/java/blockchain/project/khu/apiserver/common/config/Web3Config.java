package blockchain.project.khu.apiserver.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
public class Web3Config {

    // application.yml의 web3.node.url 값을 읽어 옴
    @Value("${web3.node.url}")
    private String nodeUrl;

    // application.yml의 web3.credentials.private-key-hex 값을 읽어 옴
    @Value("${web3.credentials.private-key-hex}")
    private String privateKeyHex;

    // application.yml의 web3.contract.address 값을 읽어 옴
    @Value("${web3.contract.address}")
    private String contractAddress;

    /**
     * 1) Web3j 인스턴스 Bean 생성
     */
    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(nodeUrl));
    }

    /**
     * 2) Credentials Bean 생성
     *    privateKeyHex(16진수 문자열) → Credentials 객체
     */
    @Bean
    public Credentials credentials() {
        return Credentials.create(privateKeyHex);
    }

    /**
     * 3) contractAddress Bean 생성
     *    컨트랙트 주소는 단순 문자열이므로 String 타입으로 등록
     */
    @Bean
    public String contractAddress() {
        return contractAddress;
    }
}