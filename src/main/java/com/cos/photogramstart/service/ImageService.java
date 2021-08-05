package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image> 인기사진() {
        return imageRepository.mPopular();
    }

    // 영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush(반영) x // 세션을 컨트롤러단까지 끌고와야함!
    @Transactional(readOnly = true)
    public Page<Image> 이미지스토리(int principalId, Pageable pageable) {
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        // 2(cos) 로그인
        // images에 좋아요 상태 담기
        // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로그인한 사람이 좋아요 한것인지 비교!!
        images.forEach(image -> {

            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach(likes -> {
                if(likes.getUser().getId() == principalId) {
                    image.setLikeState(true);
                }
            });
        });

        return images;
    }


    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); // 실제 파일 이름!
        System.out.println("===이미지 파일이름 확인===" + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName); // 경로 + 파일이름

        // 통신, IO -> 예외가 항상 발생할 수 있음
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // imageFileName => 여기서 만들어진 uuid_+파일이름
        // principalDetails.getUser -> 유저정보를 toEntity에 두개를 같이 넘기고 Image의 Entity형태로 리턴받아서 나옴
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);

//        System.out.println(imageEntity);
    }

}
