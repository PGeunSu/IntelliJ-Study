package Api.PubicData.controller;

import Api.PubicData.dto.SubstationInfo;
import Api.PubicData.repository.SubstationInfoRepository;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class HomeController {

    @Autowired
    private SubstationInfoRepository infoRepository;

    @GetMapping("/api")
    public String index(){
        return "index";
    }

    @PostMapping("/api")
    public String load_save(@RequestParam("date") String date, Model model){

        String result = "";

        try {
            String requestDate=date;
            URL url = new URL("http://openapi.seoul.go.kr:8088/" + "624676656d706b733130396a76614f52/" +
                    "json/CardSubwayStatsNew/1/700/"+requestDate);
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONObject CardSubwayStatsNew = (JSONObject)jsonObject.get("CardSubwayStatsNew");
            Long totalCount=(Long)CardSubwayStatsNew.get("list_total_count");

            JSONObject subResult = (JSONObject)CardSubwayStatsNew.get("RESULT");
            JSONArray infoArr = (JSONArray) CardSubwayStatsNew.get("row");

            for(int i=0;i<infoArr.size();i++){
                JSONObject tmp = (JSONObject)infoArr.get(i);
                SubstationInfo infoObj=new SubstationInfo(i+(long)1, (String)tmp.get("USE_DT"),(String)tmp.get("LINE_NUM"),(String)tmp.get("SUB_STA_NM"),
                        (double)tmp.get("RIDE_PASGR_NUM"), (double)tmp.get("ALIGHT_PASGR_NUM"),(String)tmp.get("WORK_DT"));
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return "redirect:/findname";
    }
}