package com.careerit.jfs.iplcorestats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator; //for top n player method

public class IplStatsServiceImpl implements IplStatsService {

    private List<Player> players;

    public IplStatsServiceImpl() {
        players = PlayerDataReaderUtil.loadPlayersData();
    }

    @Override
    public List<String> teamNames() {
        /*List<String> teamNames = new ArrayList<>();
        for (Player player : players) {
            if(!teamNames.contains(player.getTeam())) {
                teamNames.add(player.getTeam());
            }
        }
        return teamNames;*/

        return players.stream()
                .map(Player::getTeam)
                .distinct()
                .toList();
    }

    @Override
    public List<Player> playersByTeam(String teamName) {
        /*List<Player> teamPlayers = new ArrayList<>();
        for (Player player : players) {
            if(player.getTeam().equalsIgnoreCase(teamName)) {
                teamPlayers.add(player);
            }
        }
        return teamPlayers;*/
        return players.stream()
                .filter(player -> player.getTeam().equalsIgnoreCase(teamName))
                .toList();
    }

    @Override
    public List<TeamRoleCountRecord> roleCountByTeam(String teamName) {
        List<Player> teamPlayers = playersByTeam(teamName);
        Map<String,Integer> roleCountMap = new HashMap<>();
        for (Player player : teamPlayers) {
            String role = player.getRole();
            roleCountMap.put(role,roleCountMap.getOrDefault(role,0)+1);
        }
        return getTeamRoleCountRecords(teamName, roleCountMap);
    }


   //assignment starts here task 1

   @Override
   public List<TeamAmountRecord> roleAmountByTeam(String teamName) {
       List<Player> teamPlayers = playersByTeam(teamName);
       Map<String, Double> roleAmountMap = new HashMap<>();

       for (Player player : teamPlayers) {
           String role = player.getRole();
           double amount = player.getAmount();
           roleAmountMap.put(role, roleAmountMap.getOrDefault(role, 0.0) + amount);
       }

       return roleAmountMap.entrySet().stream()
               .map(entry -> new TeamAmountRecord(entry.getKey(), entry.getValue()))
               .toList();
   }


   // task 2
    @Override
    public List<TeamAmountRecord> amountByTeam(String teamName) {
        Map<String, Double> teamAmountMap = new HashMap<>();

        for (Player player : players) {
            double amount = player.getAmount();
            teamAmountMap.put(teamName, teamAmountMap.getOrDefault(teamName, 0.0) + amount);
        }

        return teamAmountMap.entrySet().stream()
                .map(entry -> new TeamAmountRecord(entry.getKey(), entry.getValue()))
                .toList();
    }

    // task 3
    @Override
    public List<TeamCountRecord> playerCountOfEachTeam() {
        Map<String, Integer> teamPlayerCountMap = new HashMap<>();
        for (Player player : players) {
            String teamName = player.getTeam();
            teamPlayerCountMap.put(teamName, teamPlayerCountMap.getOrDefault(teamName, 0) + 1);
        }

        return teamPlayerCountMap.entrySet().stream()
                .map(entry -> new TeamCountRecord(entry.getKey(), entry.getValue()))
                .toList();
    }

    // task 4
    @Override
    public Map<String, List<Player>> getTopPaidPlayersOfEachTeam() {
        Map<String,List<Player>> playerAndTeamMap = players.stream()
                .collect(Collectors.groupingBy(Player::getTeam));

        Map<String, List<Player>> topPaidPlayersMap = new HashMap<>();
        for (Map.Entry<String, List<Player>> entry : playerAndTeamMap.entrySet()) {
            List<Player> players = entry.getValue();
            double maxAmount = maxAmount(players);
            List<Player> topPaidPlayers = players.stream()
                    .filter(player -> player.getAmount() == maxAmount)
                    .toList();
            topPaidPlayersMap.put(entry.getKey(), topPaidPlayers);
        }
        return topPaidPlayersMap;
    }

    // task 5
    @Override
    public List<Player> getTopPaidPlayers() {
         double maxAmount = maxAmount(players);
         return players.stream()
                 .filter(player -> player.getAmount() == maxAmount)
                 .toList();
    }

    // task 6
    @Override
    public List<Player> getTopPaidPlayers(int n) {
        List<Player> sortedPlayers = players.stream()
                .sorted(Comparator.comparingDouble(Player::getAmount).reversed())
                .collect(Collectors.toList());

        return sortedPlayers.subList(0, Math.min(n, sortedPlayers.size()));
    }

    private static List<TeamRoleCountRecord> getTeamRoleCountRecords(String teamName, Map<String, Integer> roleCountMap) {
        List<TeamRoleCountRecord> teamRoleCountList =  new ArrayList<>();
        for (Map.Entry<String, Integer> entry : roleCountMap.entrySet()) {
            String roleName = entry.getKey();
            int count = entry.getValue();
            TeamRoleCountRecord teamRoleCountDto = new TeamRoleCountRecord(teamName, roleName, count);
            teamRoleCountList.add(teamRoleCountDto);
        }
        return teamRoleCountList;
    }

    private double maxAmount(List<Player> players) {
        return players.stream()
                .mapToDouble(Player::getAmount)
                .max()
                .orElse(0);
    }
}
