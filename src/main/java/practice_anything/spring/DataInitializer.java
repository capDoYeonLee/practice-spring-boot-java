package practice_anything.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 팀 2개 생성
        Team teamA = teamRepository.save(Team.builder().name("Team A").build());
        Team teamB = teamRepository.save(Team.builder().name("Team B").build());

        // 대량 데이터를 한번에 넣으면 메모리 터질 수 있어서 배치로 나눔
        insertMembers(teamA, 500, "A");
        insertMembers(teamB, 500, "B");
    }

    private void insertMembers(Team team, int count, String prefix) {
        int batchSize = 1000; // 1000건 단위로 나눠서 insert
        List<Member> batch = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            batch.add(Member.builder()
                    .name(prefix + "_member_" + i)
                    .team(team)
                    .build());

            if (i % batchSize == 0) {
                memberRepository.saveAll(batch);
                memberRepository.flush(); // JPA 영속성 컨텍스트 비움
                batch.clear();
                System.out.println(i + " members inserted for " + team.getName());
            }
        }

        if (!batch.isEmpty()) {
            memberRepository.saveAll(batch);
            memberRepository.flush();
        }
    }
}
