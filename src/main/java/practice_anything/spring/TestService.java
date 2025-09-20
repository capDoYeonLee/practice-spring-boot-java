package practice_anything.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonWriter;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public void findMember(Long memberId) {
        Member membr = memberRepository.findById(memberId).orElseThrow();
        Team team = member.getTeam();
        System.out.println(team.getClass());
    }

    public void nplusone() {
        System.out.println("N+1 문제 발생");
        List<Member> members = memberRepository.findAll();
        System.out.println("이제 팀의 쿼리가 나갈 차례");
        for (Member member : members) {
            System.out.println(member.getTeam().getName());
        }
    }

    public void noNPlus() {
        System.out.println("N+1 문제 해결 Fetch join");
        List<Member> members = memberRepository.findAllWithTeam();
        System.out.println("팀 쿼리 추가적으로 나가지 않음");
        for (Member member : members) {
            System.out.println(member.getTeam().getName());
        }
    }

    @Transactional
    public void saveMember(TestDto testDto) {
        Team team = Team.builder().name(testDto.getTeamName()).build();
        teamRepository.save(team);

        Member member = Member.builder()
                              .name(testDto.getMemberName())
                              .team(team).build();
        memberRepository.save(member);
    }
}
