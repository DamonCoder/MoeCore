package ltd.maimeng.core.service;

import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/06/18
 *     desc   : 存储倒计时信息的数据类
 * </pre>
 */
public class CountDownBean implements Serializable {

    /**
     * 最长秒数
     */
    private int maxCount = -1;

    /**
     * 广播Action
     */
    private String action = "";

    /**
     * 是否循环倒计时
     */
    private boolean isLoop = false;

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }

//    @Override
//    public boolean equals(Object o) {
//        CountDownBean that = (CountDownBean) o;
//        return this.action.equals(that.action);
//    }

    @Override
    public int hashCode() {
        return Objects.hash(maxCount, action, isLoop);
    }

    /**
     * 发送广播的key
     */
    public static final String BEAN = "CountDownBean";

    public static final String ACTION_LOGIN_VCODE = "countdown_action_login_vcode";
    public static final String ACTION_AUDIO_PLAY = "countdown_action_audio_play";
    public static final String ACTION_AUDIO_PLAY_EDIT_INTRODUCE = "countdown_action_audio_play_edit_introduce";
    public static final String ACTION_KTV_PREPARE = "countdown_action_ktv_prepare";

    public static final String ACTION_TASK_PUBLISH_01 = "countdown_action_task_publish_01_";
    public static final String ACTION_TASK_PUBLISH_02 = "countdown_action_task_publish_02_";
    public static final String ACTION_TASK_CLAIM_01 = "countdown_action_task_claim_01_";
    public static final String ACTION_TASK_CLAIM_02 = "countdown_action_task_claim_02_";
    public static final String ACTION_TASK_CLAIM_03 = "countdown_action_task_claim_03_";

    public static final String ACTION_TASK_CENTER_01 = "countdown_action_task_center_01_";
    public static final String ACTION_TASK_CENTER_02 = "countdown_action_task_center_02_";
    public static final String ACTION_TASK_CENTER_03 = "countdown_action_task_center_03_";
    public static final String ACTION_TASK_CENTER_04 = "countdown_action_task_center_04_";

    public static final String ACTION_TASK_DISPUTATION = "countdown_action_task_disputation";

    public static final String ACTION_TASK_CENTER_DISPUTE = "countdown_action_task_center_dispute_";
    public static final String ACTION_TASK_CENTER_PUBLISH = "countdown_action_task_center_publish_";
    public static final String ACTION_TASK_CENTER_CLAIM_01 = "countdown_action_task_center_claim_01_";
    public static final String ACTION_TASK_CENTER_CLAIM_02 = "countdown_action_task_center_claim_02_";
    public static final String ACTION_TASK_CENTER_CLAIM_03_DISPUTE = "countdown_action_task_center_claim_03_dispute_";
    public static final String ACTION_TASK_CENTER_CLAIM_03_VERIFY = "countdown_action_task_center_claim_03_verify_";

    public static final String ACTION_TASK_CENTER_PUBLISH_DETAIL = "countdown_action_task_center_publish_detail";
    public static final String ACTION_TASK_CENTER_PUBLISH_DETAIL_01 = "countdown_action_task_center_publish_detail_01_";
    public static final String ACTION_TASK_CENTER_PUBLISH_DETAIL_04 = "countdown_action_task_center_publish_detail_04_";
    public static final String ACTION_TASK_CENTER_PUBLISH_DETAIL_05 = "countdown_action_task_center_publish_detail_05_";

    public static final String ACTION_TASK_CENTER_CLAIM_DETAIL = "countdown_action_task_center_claim_detail";
    public static final String ACTION_TASK_CENTER_EVIDENCE_DETAIL = "countdown_action_task_center_evidence_detail";
    public static final String ACTION_TASK_CENTER_EVIDENCE_SUBMIT = "countdown_action_task_center_evidence_submit";

    public static final String ACTION_FRAGMENT_MINE_TASK_PUBLISH = "countdown_action_fragment_mine_task_publish_";
    public static final String ACTION_FRAGMENT_MINE_TASK_CLAIM_01 = "countdown_action_fragment_mine_task_claim_01_";
    public static final String ACTION_FRAGMENT_MINE_TASK_CLAIM_02 = "countdown_action_fragment_mine_task_claim_02_";
    public static final String ACTION_FRAGMENT_MINE_TASK_CLAIM_03_A = "countdown_action_fragment_mine_task_claim_03_A_";
    public static final String ACTION_FRAGMENT_MINE_TASK_CLAIM_03_B = "countdown_action_fragment_mine_task_claim_03_B_";

    public static final String TASK_PROGRESS = "countdown_action_task_center_publish_detail";
    public static final String TASK_INVITE_FRIENDS = "countdown_action_invite_friends";

    public static final String HOME_BROADCAST_REFRESH = "countdown_home_broadcast_refresh";

    public static final String ACTION_ONLINE_TASK_PUBLISH_02 = "countdown_action_online_task_publish_02_";


    public static final String ACTION_TASK_DETAIL_SUBMIT_EVIDENCE_TIME_LIST = "countdown_action_task_detail_submitEvidenceTimeLimit";
    public static final String ACTION_TASK_DETAIL_VERIFY_TIME_LIST = "countdown_action_task_detail_verifyTimeLimit";

    public static final String ACTION_PUBLISHER_PUBLICITY = "countdown_action_publisher_publicity_";
    public static final String ACTION_PUBLISHER_DETAIL_PUBLICITY = "countdown_action_publisher_detail_publicity";

}
