package practice_anything.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestPropagationService {

    private final MemberRepository memberRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void propagationSaveMember(Team team) {
        String name = "tester";
        try {
            team.updateTeamName("check update");
        } catch (Exception e) {

        }

        Member member = Member.builder()
                              .name(name)
                              .team(team)
                              .build();

        memberRepository.save(member);
    }
}
