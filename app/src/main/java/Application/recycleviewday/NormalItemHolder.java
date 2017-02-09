package Application.recycleviewday;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gab.myapplication.R;


/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class NormalItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView out_trade_no;
    public TextView amout;
    public TextView payType;
    public TextView time;
    public TextView today;
    public ImageView imageType;


    private CustomClickListener listener;
    private CustomItemLongClickListener longClickListener;
    private Context context;

    public NormalItemHolder(Context context, View itemView, CustomClickListener listener,
                            CustomItemLongClickListener longClickListener) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        this.longClickListener = longClickListener;
        time = (TextView) itemView.findViewById(R.id.time);
        out_trade_no = (TextView) itemView.findViewById(R.id.out_trade_no);
        amout = (TextView) itemView.findViewById(R.id.amout);
        today = (TextView) itemView.findViewById(R.id.today);
        payType = (TextView) itemView.findViewById(R.id.payType);
        imageType= (ImageView) itemView.findViewById(R.id.imageType);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            itemView.setBackgroundResource(R.drawable.recycler_bg);
            listener.setClickListtener(v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClickListener != null) {
            itemView.setBackgroundResource(R.drawable.recycler_bg);
            longClickListener.setItemLongClick(v, getAdapterPosition());
        }
        return true;
    }
}
