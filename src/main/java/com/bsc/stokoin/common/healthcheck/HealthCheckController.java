package com.bsc.stokoin.common.healthcheck;

import com.bsc.stokoin.user.application.UserFacade;
import com.bsc.stokoin.user.domain.UserValidator;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Tag(name = "테스트 API", description = "테스트 용도")
@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final UserFacade userFacade;
    private final UserValidator userValidator;

    @Value("${app.firebase-bucket}")
    private String firebaseBucket;

    @GetMapping("/health")
    public String healthCheck(){
        return "OK";
    }

////    @PreAuthorize("permitAll()") // 인증된 사용자든 아니든 접근 가능 (임시 회원 생성)
//    @Operation(summary = "임시 회원 생성", description = "현재 Oauth2 로그인을 지원하지 않으므로 임시 회원을 생성합니다.")
//    @PostMapping("/temp/users")
//    public ResponseEntity<CommonResponse<AuthResponseDto>> saveTempInfo(@Validated @RequestBody UserTempSaveRequest userTempSaveRequest,
//                                                                        BindingResult errors){
//        userValidator.validate(userTempSaveRequest, errors);
//        if (errors.hasErrors()){
//            throw new ValidatedException(errors);
//        }
//
//        return ResponseEntity.ok(CommonResponse.success(userFacade.saveTempInfo(userTempSaveRequest)));
//    }

    @PostMapping(value = "/upload-test",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@RequestPart MultipartFile multipartFile) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        ByteArrayInputStream content = new ByteArrayInputStream(multipartFile.getBytes());
        Blob blob = bucket.create(multipartFile.getName(), content, multipartFile.getContentType());
        return blob.getMediaLink();
    }

}
