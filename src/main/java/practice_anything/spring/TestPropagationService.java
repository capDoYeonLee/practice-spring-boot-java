package practice_anything.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class TestPropagationService {

    private final MemberRepository memberRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    //@Transactional(propagation = Propagation.MANDATORY)
    public void propagationSaveMember(Team team) {

        String txName = TransactionSynchronizationManager.getCurrentTransactionName();
        System.out.println("propagation save member method transaction 현재 트랜잭션 이름 = " + txName);

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
