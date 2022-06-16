package ltd.maimeng.core.ui.dialog.builder;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public class MoeDialogBuilder extends MoeBaseDialogBuilder<MoeDialogBuilder> {

    public MoeDialogBuilder(Context context) {
        super(context);
    }

    public MoeDialogBuilder setMessage(@NonNull String message){
        dialogBuilder.setMessage(message);
        return this ;
    }

    public MoeDialogBuilder setMessage(@StringRes int messageId){
        dialogBuilder.setMessage(messageId);
        return this ;
    }
}
