package fansirsqi.xposed.sesame.task.antForest;

import org.json.JSONObject;

import fansirsqi.xposed.sesame.util.Log;
import fansirsqi.xposed.sesame.util.ResUtil;

public class GreenLife {
    public static final String TAG = GreenLife.class.getSimpleName();

    /** 森林集市 */
    public static void ForestMarket(String sourceType) {
        try {
            JSONObject jo = new JSONObject(AntForestRpcCall.consultForSendEnergyByAction(sourceType));
            if (ResUtil.checkSuccess(TAG,jo)) {
                JSONObject data = jo.getJSONObject("data");
                if (data.optBoolean("canSendEnergy", false)) {
                    Thread.sleep(5000);
                    jo = new JSONObject(AntForestRpcCall.sendEnergyByAction(sourceType));
                    if (ResUtil.checkSuccess(TAG,jo)) {
                        data = jo.getJSONObject("data");
                        if (data.optBoolean("canSendEnergy", false)) {
                            int receivedEnergyAmount = data.getInt("receivedEnergyAmount");
                            Log.forest("集市逛街👀[获得:能量" + receivedEnergyAmount + "g]");
                        }
                    }
                }
            } else {
                Log.runtime(TAG, jo.getJSONObject("data").getString("resultCode"));
                Thread.sleep(5000);
            }
        } catch (Throwable t) {
            Log.runtime(TAG, "sendEnergyByAction err:");
            Log.printStackTrace(TAG, t);
        }
    }
}