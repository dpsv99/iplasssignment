package com.careerit.jfs.iplcorestats;

import java.util.List;
import java.util.Map;

public class IplStatsManager {

    public static void main(String[] args) {

        IplStatsService iplStatsService = new IplStatsServiceImpl();
        // 1. Get team names
        List<String> teamNames = iplStatsService.teamNames();
        System.out.println("Task 1:Team names : "+teamNames);

        // 2. Get players by team name
        String teamName = "RCB";
        List<Player> players = iplStatsService.playersByTeam(teamName);
        System.out.println("\nTask 2: The team "+teamName+" has "+players.size()+" players\n");

        // 3. Get role count by team name
        List<TeamRoleCountRecord> teamRoleCountRecords = iplStatsService.roleCountByTeam(teamName);
        System.out.println("Task 3:Role count by team : "+teamRoleCountRecords);

        //4. Get role amount by team
        List<TeamAmountRecord> obj = iplStatsService.roleAmountByTeam(teamName);
        System.out.println("\nTask 4:Team amount by role: "+obj);

        //5. Get role amount by team
        List<TeamAmountRecord> obj2 = iplStatsService.amountByTeam(teamName);
        System.out.println("\nTask 5: Team amount by role: "+obj2);

        //6. Get player count of each team
        List<TeamCountRecord> obj3 = iplStatsService.playerCountOfEachTeam();
        System.out.println("\nTask 6: Count of layers by team: "+obj3);

        //7. Top paid player of each team
        Map<String,List<Player>> obj4 = iplStatsService.getTopPaidPlayersOfEachTeam();
        System.out.println("\nTask 7: Top paid player of each team "+obj4);

        //8. Get top paid players
        List<Player> obj5 = iplStatsService.getTopPaidPlayers();
        System.out.println("\nTask 8: Top paid players "+obj5);

        //9. Get N top players among all teams
        int N =5;
        List<Player> obj6 = iplStatsService.getTopPaidPlayers(N);
        System.out.println("\nTop "+N+" players "+obj6);
    }
}
