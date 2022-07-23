package com.sss.domain.meeting;

import com.sss.TokenGenerator;
import com.sss.domain.BaseEntity;
import com.sss.domain.category.Category;
import com.sss.domain.category.item.CategoryItem;
import com.sss.domain.meeting.fragment.MeetLocation;
import com.sss.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "manna_meeting",
        indexes = @Index(columnList = "token", name = "IDX_meetingToken"),
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_meetingToken", columnNames = {"token"}),
        }
)
public class Meeting extends BaseEntity {
    private static final String TOKEN_PREFIX = "met_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 31)
    private String token;
    @Column(length = 31)
    private String categoryItemName;
    @Column(length = 63)
    private String title;
    @Column(length = 255)
    private String introductionImage;
    @Column(length = 1023)
    private String introductionContent;
    private ZonedDateTime meetDateTime;
    private Boolean offline;
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private RecruitmentMethod recruitmentMethod;
    @Embedded
    private MeetLocation meetLocation;
    @Column(length = 511)
    private String approvalSystemQuestion;
    private Integer participantsNumber;
    private Boolean free;
    private Integer entryFee;
    @Column(length = 511)
    private String entryFeeDescription;
    private Boolean opened;
    @Column(length = 511)
    private String pageLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "makeMemberId", nullable = false, foreignKey = @ForeignKey(name = "FK_meeting_makeMember"))
    private Member makeMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false, foreignKey = @ForeignKey(name = "FK_meeting_category"))
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryItemId", foreignKey = @ForeignKey(name = "FK_meeting_categoryItem"))
    private CategoryItem categoryItem;

    @Getter
    @RequiredArgsConstructor
    public enum RecruitmentMethod {
        FIRST_COME("선착순"),
        APPROVAL("승인제");

        private final String title;

        private static final Map<String, RecruitmentMethod> descriptionMap = Collections.unmodifiableMap(Stream.of(values())
                .collect(Collectors.toMap(RecruitmentMethod::getTitle, Function.identity())));
        public static Optional<RecruitmentMethod> of(String description) {
            return Optional.ofNullable(descriptionMap.get(description));
        }
    }

    @Builder
    public Meeting(
            String categoryItemName,
            String title,
            String introductionImage,
            String introductionContent,
            ZonedDateTime meetDateTime,
            Boolean offline,
            MeetLocation meetLocation,
            RecruitmentMethod recruitmentMethod,
            String approvalSystemQuestion,
            Integer participantsNumber,
            Boolean free,
            Integer entryFee,
            String entryFeeDescription,
            Boolean opened,
            String pageLink
    ) {
        this.token = TokenGenerator.randomCharacterWithPrefix(TOKEN_PREFIX);
        this.categoryItemName = categoryItemName;
        this.title = title;
        this.introductionImage = introductionImage;
        this.introductionContent = introductionContent;
        this.meetDateTime = meetDateTime;
        this.offline = offline;
        this.meetLocation = meetLocation;
        this.recruitmentMethod = recruitmentMethod;
        this.approvalSystemQuestion = approvalSystemQuestion;
        this.participantsNumber = participantsNumber;
        this.free = free;
        this.entryFee = entryFee;
        this.entryFeeDescription = entryFeeDescription;
        this.opened = opened;
        this.pageLink = pageLink;
    }
}
