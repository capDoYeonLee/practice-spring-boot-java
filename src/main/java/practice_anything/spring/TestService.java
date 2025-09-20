package practice_anything.spring;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonWriter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    @Autowired
    @Lazy
    private TestService self;

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TestPropagationService testPropagationService;

    public void findMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
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

    @Transactional
    public void saveTeam(TeamNameDto teamNameDto) {
        Team team = Team.builder()
                        .name(teamNameDto.getTeamName()).build();
        team = teamRepository.save(team);
        testPropagationService.propagationSaveMember(team);
        //self.propagationSaveMember(team); // self injection??
        //propagationSaveMember(team);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW) // 항상 새로운 트랜잭션 생성 // 기존 트랜잭션 잠시 중단
    //@Transactional(propagation = Propagation.SUPPORTS) // 이전에 트랜잭션이 있다면 참여 // 만약 없다면 트랜잭션 없이 실행
    public void propagationSaveMember(Team team) {
        String name = "tester";
        team.updateTeamName("check update");
        Member member = Member.builder()
                              .name(name)
                              .team(team)
                              .build();

        memberRepository.save(member);
    }
}
