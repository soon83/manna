package com.sss.config;

import com.sss.domain.category.Category;
import com.sss.domain.category.item.CategoryItem;
import com.sss.domain.member.Member;
import com.sss.domain.member.interest.Interest;
import com.sss.exception.member.MemberNotFoundException;
import com.sss.infrastructure.category.CategoryItemRepository;
import com.sss.infrastructure.category.CategoryRepository;
import com.sss.infrastructure.member.MemberInterestRepository;
import com.sss.infrastructure.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@RequiredArgsConstructor
public class InitDB implements InitializingBean {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberInterestRepository memberInterestRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;

    @Override
    public void afterPropertiesSet() {
        makeCategoryList(); // 관심사 만들기
        makeMember(); // 회원 만들기
    }

    private void makeMember() {
        try {
            memberRepository.findByLoginId("admin")
                    .orElseThrow(MemberNotFoundException::new);
        } catch (Exception e) {
            var admin = memberRepository.save(Member.builder()
                    .loginId("admin")
                    .loginPassword(passwordEncoder.encode("1234"))
                    .name("하츄핑")
                    .email("admin@email.com")
                    .avatar("/avatar/file/path")
                    .nickName("사랑의하츄핑")
                    .selfIntroduction("나는 하츄핑이야 츄")
                    .build());

            var categoryItemIdList = Arrays.asList(1L, 2L, 4L, 8L, 10L, 12L, 14L, 16L, 18L, 20L);
            var categoryItemList = categoryItemRepository.findAllById(categoryItemIdList);
            categoryItemList.forEach(categoryItem -> {
                memberInterestRepository.save(Interest.builder()
                        .categoryItem(categoryItem)
                        .member(admin)
                        .build());
            });


        }
    }

    private void makeCategoryList() {
        var categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty()) {
            AtomicInteger index01 = new AtomicInteger(0);
            var category01 = Category.builder().title("문화/예술").ordering(0).build();
            List<String> categoryItemList01 = Arrays.asList(
                    "전시", "영화", "뮤지컬", "공연", "디자인", "박물관", "연극", "콘서트", "연주회", "페스티벌"
            );
            categoryItemList01.forEach(item -> category01.getCategoryItemList().add(
                    CategoryItem.builder().title(item).ordering(index01.getAndIncrement()).category(category01).build())
            );

            AtomicInteger index02 = new AtomicInteger(0);
            var category02 = Category.builder().title("운동/액티비티").ordering(1).build();
            List<String> categoryItemList02 = Arrays.asList(
                    "러닝", "등산", "트래킹", "산책", "플로깅", "헬스", "클라이밍", "자전거", "서핑", "스키",
                    "보드", "배드민턴", "테니스", "축구", "농구", "탁구", "볼링", "골프", "스포츠경기"
            );
            categoryItemList02.forEach(item -> category02.getCategoryItemList().add(
                    CategoryItem.builder().title(item).ordering(index02.getAndIncrement()).category(category02).build())
            );

            AtomicInteger index03 = new AtomicInteger(0);
            var category03 = Category.builder().title("푸드/드링크").ordering(2).build();
            List<String> categoryItemList03 = Arrays.asList(
                    "맛집투어", "카페", "티룸", "디저트", "비건", "파인다이닝", "요리", "페어링", "커피", "와인",
                    "칵테일", "맥주", "위스키", "전통주"
            );
            categoryItemList03.forEach(item -> category03.getCategoryItemList().add(
                    CategoryItem.builder().title(item).ordering(index03.getAndIncrement()).category(category03).build())
            );

            AtomicInteger index04 = new AtomicInteger(0);
            var category04 = Category.builder().title("여행/나들이").ordering(3).build();
            List<String> categoryItemList04 = Arrays.asList(
                    "복합문화공간", "테마파크", "피크닉", "드라이브", "캠핑", "국내여행", "해외여행"
            );
            categoryItemList04.forEach(item -> category04.getCategoryItemList().add(
                    CategoryItem.builder().title(item).ordering(index04.getAndIncrement()).category(category04).build())
            );

            AtomicInteger index05 = new AtomicInteger(0);
            var category05 = Category.builder().title("취미").ordering(4).build();
            List<String> categoryItemList05 = Arrays.asList(
                    "보드게임", "사진", "방탈출", "VR", "음악감상", "댄스", "악기연주", "명상", "대화", "봉사활동",
                    "컬러링", "캘리그라피", "가드닝", "반려동물", "만화"
            );
            categoryItemList05.forEach(item -> category05.getCategoryItemList().add(
                    CategoryItem.builder().title(item).ordering(index05.getAndIncrement()).category(category05).build())
            );

            AtomicInteger index06 = new AtomicInteger(0);
            var category06 = Category.builder().title("창작").ordering(5).build();
            List<String> categoryItemList06 = Arrays.asList(
                    "글쓰기", "드로잉", "영상편집", "공예", "DIY", "소설", "에세이", "시", "작곡"
            );
            categoryItemList06.forEach(item -> category06.getCategoryItemList().add(
                    CategoryItem.builder().title(item).ordering(index06.getAndIncrement()).category(category06).build())
            );

            AtomicInteger index07 = new AtomicInteger(0);
            var category07 = Category.builder().title("연애").ordering(6).build();
            List<String> categoryItemList07 = Arrays.asList(
                    "모태솔로", "돌싱", "결혼전제", "연애만"
            );
            categoryItemList07.forEach(item -> category07.getCategoryItemList().add(
                    CategoryItem.builder().title(item).ordering(index07.getAndIncrement()).category(category07).build())
            );

            AtomicInteger index08 = new AtomicInteger(0);
            var category08 = Category.builder().title("성장/자기계발").ordering(7).build();
            List<String> categoryItemList08 = Arrays.asList(
                    "독서", "스터디", "외국어", "재테크", "브랜딩", "커리어", "사이드 프로젝트"
            );
            categoryItemList08.forEach(item -> category08.getCategoryItemList().add(
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
