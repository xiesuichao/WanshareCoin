package com.wanshare.crush.market.trade.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wanshare.crush.market.R;
import com.wanshare.crush.market.trade.view.widget.ProgressDrawable;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.utils.ArithUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * 委盘
 */

public class QuotationEntrustAdapter extends BaseAdapter {

    public static final String ZERO = "0";
    private Context context;
    private List<String> leftVolList;
    private List<String> leftPriceList;
    private List<String> rightVolList;
    private List<String> rightPriceList;

    private String maxLeftVol;
    private String maxRightVol;

    private int pricePrecision = 8;
    private int volumePrecision = 8;

    private int mLeftBgColor;
    private int mRightBgColor;

    private onItemClickListener mLeftOnItemClickListener;
    private onItemClickListener mRightOnItemClickListener;

    private View.OnClickListener mLeftOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtil.d("leon ==left position:" + v.getTag());
            if (v.getTag() != null && v.getTag() instanceof Integer) {
                Integer position = (Integer) v.getTag();
                String leftPrice = position < leftPriceList.size() ? leftPriceList.get(position) : ZERO;
                if (mLeftOnItemClickListener != null) {
                    mLeftOnItemClickListener.onClick(leftPrice, position);
                }

            }
        }
    };

    private View.OnClickListener mRightOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtil.d("leon ==right position:" + v.getTag());
            if (v.getTag() != null && v.getTag() instanceof Integer) {
                Integer position = (Integer) v.getTag();
                String rightPrice = position < rightPriceList.size() ? rightPriceList.get(position) : ZERO;
                if (mRightOnItemClickListener != null) {
                    mRightOnItemClickListener.onClick(rightPrice, position);
                }
            }
        }
    };

    public QuotationEntrustAdapter(Context context, List<String> leftVolList, List<String> leftPriceList,
                                   List<String> rightVolList, List<String> rightPriceList) {
        this.context = context;
        this.leftVolList = leftVolList;
        this.leftPriceList = leftPriceList;
        this.rightVolList = rightVolList;
        this.rightPriceList = rightPriceList;
        mLeftBgColor = ContextCompat.getColor(context, R.color.color_green_light_alpha_10);
        mRightBgColor = ContextCompat.getColor(context, R.color.color_red_light_alpha_10);
        calcMax();
    }

    @Override
    public int getCount() {
        if (leftVolList == null) return 0;
        if (leftVolList.size() > rightVolList.size()) {
            return leftVolList.size();
        } else {
            return rightVolList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (leftVolList.size() > rightVolList.size()) {
            return leftVolList.get(position);
        } else {
            return rightVolList.get(position);
        }
    }

    public void updateData(List<String> leftSalelist, List<String> leftPricelist, List<String> rightSalelist, List<String> rightPricelist) {
        this.leftVolList = leftSalelist;
        this.leftPriceList = leftPricelist;
        this.rightVolList = rightSalelist;
        this.rightPriceList = rightPricelist;

        calcMax();

        notifyDataSetChanged();
    }

    private void calcMax() {
        maxLeftVol = ZERO;
        for (String left : leftVolList) {
            try {
                if (ArithUtil.bigVol(left, maxLeftVol)) {
                    maxLeftVol = left;
                }
            } catch (Exception e) {
                LogUtil.ex(e);
            }
        }

        if (TextUtils.isEmpty(maxLeftVol)) {
            maxLeftVol = ZERO;
        }

        maxRightVol = ZERO;
        for (String right : rightVolList) {
            try {
                if (ArithUtil.bigVol(right, maxRightVol)) {
                    maxRightVol = right;
                }
            } catch (Exception ex) {
                LogUtil.ex(ex);
            }
        }

        if (TextUtils.isEmpty(maxRightVol)) {
            maxRightVol = ZERO;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder iHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.market_item_entrust, null);
            iHolder = new ItemViewHolder();
            iHolder.tvNumber = convertView.findViewById(R.id.tv_number);
            iHolder.llLeft = convertView.findViewById(R.id.ll_left);
            iHolder.tvLeftVol = convertView.findViewById(R.id.tv_left_vol);
            iHolder.tvLeftPrice = convertView.findViewById(R.id.tv_left_price);
            iHolder.llRight = convertView.findViewById(R.id.ll_right);
            iHolder.tvRightVol = convertView.findViewById(R.id.tv_right_vol);
            iHolder.tvRightPrice = convertView.findViewById(R.id.tv_right_price);

            convertView.setTag(iHolder);
        } else {
            iHolder = (ItemViewHolder) convertView.getTag();
        }

        String leftVol = position < leftVolList.size() ? leftVolList.get(position) : null;
        String rightVol = position < rightVolList.size() ? rightVolList.get(position) : null;
        String leftPrice = position < leftPriceList.size() ? leftPriceList.get(position) : null;
        String rightPrice = position < rightPriceList.size() ? rightPriceList.get(position) : null;

        iHolder.tvNumber.setText((position + 1) + "");

        float leftPercent = (float) Math.sqrt(Double.valueOf(ArithUtil.div(leftVol, maxLeftVol)));
        setDrawable(iHolder.llLeft, mLeftBgColor, ProgressDrawable.PROGRESS_ORIENTATION_RIGHT_TO_LEFT, leftPercent);

        iHolder.llLeft.setTag(position);
        iHolder.llLeft.setOnClickListener(mLeftOnClickListener);

        iHolder.tvLeftVol.setText(leftVol);
        iHolder.tvLeftPrice.setText(leftPrice);
        iHolder.tvRightVol.setText(rightVol);
        iHolder.tvRightPrice.setText(rightPrice);
        float rightPercent = (float) Math.sqrt(Double.valueOf(ArithUtil.div(rightVol, maxRightVol)));
        setDrawable(iHolder.llRight, mRightBgColor, ProgressDrawable.PROGRESS_ORIENTATION_LEFT_TO_RIGHT, rightPercent);
        iHolder.llRight.setTag(position);
        iHolder.llRight.setOnClickListener(mRightOnClickListener);
        return convertView;

    }

    private void setDrawable(LinearLayout ll, int bgColor, int oritation, float percent) {
        ProgressDrawable leftDrawable;
        if (ll.getBackground() instanceof ProgressDrawable) {
            leftDrawable = (ProgressDrawable) ll.getBackground();
        } else {
            leftDrawable = new ProgressDrawable(bgColor, oritation);
        }
        leftDrawable.setPercent(percent);
        ll.setBackground(leftDrawable);
    }

    private String formatPrice(String price) {
        if (price == null) {
            return "";
        }
        return new BigDecimal(price).setScale(pricePrecision, BigDecimal.ROUND_DOWN).toPlainString();
    }

    private String formatVol(String vol) {
        if (vol == null) {
            return "";
        }
        return new BigDecimal(vol).setScale(volumePrecision, BigDecimal.ROUND_DOWN).toPlainString();
    }

    public void setPrecision(int pricePrecision, int volumePrecision) {
        this.pricePrecision = pricePrecision;
        this.volumePrecision = volumePrecision;
    }


    private static class ItemViewHolder {
        TextView tvNumber;
        LinearLayout llLeft;
        TextView tvLeftVol;
        TextView tvLeftPrice;
        LinearLayout llRight;
        TextView tvRightPrice;
        TextView tvRightVol;
    }

    public interface onItemClickListener {
        void onClick(String price, int position);
    }

    public void setOnLeftItemClickListener(onItemClickListener listener) {
        this.mLeftOnItemClickListener = listener;
    }

    public void setOnRightItemClickListener(onItemClickListener listener) {
        this.mRightOnItemClickListener = listener;
    }
}
