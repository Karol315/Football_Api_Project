package ServerResp;

import ServerResp.SimpleObjects.Team;
import ServerResp.Wrappers.PlayerStatisticsWrapper;
import ServerResp.Wrappers.SquadsWrapper;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class test {
    public static void main(String[] args) {

        String KEY = "3a8e79e75eef3edc965de9344d41dd01";
        SquadsWrapper squadsWrapper = new SquadsWrapper(33);
        System.out.println(squadsWrapper.getTeam());
        System.out.println(squadsWrapper.getPlayers());

        System.out.println("##############################################################");
        System.out.println("##############################################################");



        PlayerStatisticsWrapper playerStatisticsWrapper = new PlayerStatisticsWrapper(squadsWrapper.getPlayers().getFirst().getId());
        System.out .println(playerStatisticsWrapper.getPlayer());
        System.out .println(playerStatisticsWrapper.getStatistics().getFirst());


    }


}

