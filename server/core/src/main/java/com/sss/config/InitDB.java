package com.sss.config;

import com.sss.domain.category.Category;
import com.sss.domain.category.item.CategoryItem;
import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberService;
import com.sss.infrastructure.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@RequiredArgsConstructor
public class InitDB implements InitializingBean {

    private final MemberService memberService;
    private final CategoryRepository categoryRepository;

    @Override
    public void afterPropertiesSet() {
        makeAdmin();
        makeCategories();
    }

    private void makeAdmin() {
        try {
            memberService.retrieveLoginMember("admin");
        } catch (Exception e) {
            memberService.registerMember(MemberCommand.RegisterMember.builder()
                            .loginId("admin")
                            .loginPassword("1234")
                            .name("하츄핑")
                            .email("admin@email.com")
                            .avatar("/avatar/file/path")
                            .nickName("사랑의하츄핑")
                            .selfIntroduction("나는 하츄핑이야 츄")
                            .categories(List.of(1,2,3,4))
                            .categoryItems(List.of(5,6,7,8))
                    .build());
        }
    }

    private void makeCategories() {
        var categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            AtomicInteger index01 = new AtomicInteger(0);
            var category01 = Category.builder().title("문화/예술").ordering(0).build();
            List<String> categoryItems01 = Arrays.asList(
                    "전시", "영화", "뮤지컬", "공연", "디자인", "박물관", "연극", "콘서트", "연주회", "페스티벌"
            );
            categoryItems01.forEach(item -> category01.getCategoryItems().add(
                    CategoryItem.builder().title(item).ordering(index01.getAndIncrement()).category(category01).build())
            );

            AtomicInteger index02 = new AtomicInteger(0);
            var category02 = Category.builder().title("운동/액티비티").ordering(1).build();
            List<String> categoryItems02 = Arrays.asList(
                    "러닝", "등산", "트래킹", "산책", "플로깅", "헬스", "클라이밍", "자전거", "서핑", "스키",
                    "보드", "배드민턴", "테니스", "축구", "농구", "탁구", "볼링", "골프", "스포츠경기"
            );
            categoryItems02.forEach(item -> category02.getCategoryItems().add(
                    CategoryItem.builder().title(item).ordering(index02.getAndIncrement()).category(category02).build())
            );

            AtomicInteger index03 = new AtomicInteger(0);
            var category03 = Category.builder().title("푸드/드링크").ordering(2).build();
            List<String> categoryItems03 = Arrays.asList(
                    "맛집투어", "카페", "티룸", "디저트", "비건", "파인다이닝", "요리", "페어링", "커피", "와인",
                    "칵테일", "맥주", "위스키", "전통주"
            );
            categoryItems03.forEach(item -> category03.getCategoryItems().add(
                    CategoryItem.builder().title(item).ordering(index03.getAndIncrement()).category(category03).build())
            );

            AtomicInteger index04 = new AtomicInteger(0);
            var category04 = Category.builder().title("여행/나들이").ordering(3).build();
            List<String> categoryItems04 = Arrays.asList(
                    "복합문화공간", "테마파크", "피크닉", "드라이브", "캠핑", "국내여행", "해외여행"
            );
            categoryItems04.forEach(item -> category04.getCategoryItems().add(
                    CategoryItem.builder().title(item).ordering(index04.getAndIncrement()).category(category04).build())
            );

            AtomicInteger index05 = new AtomicInteger(0);
            var category05 = Category.builder().title("취미").ordering(4).build();
            List<String> categoryItems05 = Arrays.asList(
                    "보드게임", "사진", "방탈출", "VR", "음악감상", "댄스", "악기연주", "명상", "대화", "봉사활동",
                    "컬러링", "캘리그라피", "가드닝", "반려동물", "만화"
            );
            categoryItems05.forEach(item -> category05.getCategoryItems().add(
                    CategoryItem.builder().title(item).ordering(index05.getAndIncrement()).category(category05).build())
            );

            AtomicInteger index06 = new AtomicInteger(0);
            var category06 = Category.builder().title("창작").ordering(5).build();
            List<String> categoryItems06 = Arrays.asList(
                    "글쓰기", "드로잉", "영상편집", "공예", "DIY", "소설", "에세이", "시", "작곡"
            );
            categoryItems06.forEach(item -> category06.getCategoryItems().add(
                    CategoryItem.builder().title(item).ordering(index06.getAndIncrement()).category(category06).build())
            );

            AtomicInteger index07 = new AtomicInteger(0);
            var category07 = Category.builder().title("연애").ordering(6).build();
            List<String> categoryItems07 = Arrays.asList(
                    "모태솔로", "돌싱", "결혼전제", "연애만"
            );
            categoryItems07.forEach(item -> category07.getCategoryItems().add(
                    CategoryItem.builder().title(item).ordering(index07.getAndIncrement()).category(category07).build())
            );

            AtomicInteger index08 = new AtomicInteger(0);
            var category08 = Category.builder().title("성장/자기계발").ordering(7).build();
            List<String> categoryItems08 = Arrays.asList(
                    "독서", "스터디", "외국어", "재테크", "브랜딩", "커리어", "사이드 프로젝트"
            );
            categoryItems08.forEach(item -> category08.getCategoryItems().add(
                    CategoryItem.builder().title(item).ordering(index08.getAndIncrement()).category(category08).build())
            );

            categoryRepository.save(category01);
            categoryRepository.save(category02);
            categoryRepository.save(category03);
            categoryRepository.save(category04);
            categoryRepository.save(category05);
            categoryRepository.save(category06);
            categoryRepository.save(category07);
            categoryRepository.save(category08);
        }
    }
}