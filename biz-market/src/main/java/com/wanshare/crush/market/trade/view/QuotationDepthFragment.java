package com.wanshare.crush.market.trade.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guoziwei.klinelib.chart.ChartInfoViewHandler;
import com.guoziwei.klinelib.chart.CustomCombinedChart;
import com.guoziwei.klinelib.util.DoubleUtil;
import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.R2;
import com.wanshare.crush.market.trade.contract.QuotationDepthContract;
import com.wanshare.crush.market.trade.model.bean.DepthBean;
import com.wanshare.crush.market.trade.presenter.QuotationDepthPresenter;

import org.reactivestreams.Subscription;

import java.util.ArrayList;

import java.util.List;

import butterknife.BindView;


/**
 * 深度
 * </br>
 * Date: 2018/8/15 17:42
 *
 * @author hemin
 */
public class QuotationDepthFragment extends BaseFragment<QuotationDepthContract.Presenter> implements QuotationDepthContract.View {

    // 右边Y轴字体大小
    public static final int AXIS_RIGHT_TEXT_SIZE = 8;

    protected int mAxisColor;
    @BindView(R2.id.chart_depth)
    CustomCombinedChart mChartDepth;

    @BindView(R2.id.ll_info_view)
    LinearLayout mLlInfoView;

    @BindView(R2.id.tv_price)
    TextView mTvPrice;

    @BindView(R2.id.tv_vol)
    TextView mTvVol;

    private LineDataSet mLineDataSetLeft;
    private LineDataSet mLineDataSetRight;

    private List<String> mXAxisLabels = new ArrayList<>();
    private Subscription subscription;
    private String pair;

    public static QuotationDepthFragment newInstance(String pair) {

        Bundle args = new Bundle();
        args.putString(IntentConstant.EXTRA_PAIR, pair);
        QuotationDepthFragment fragment = new QuotationDepthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected QuotationDepthContract.Presenter getPresenter() {
        return new QuotationDepthPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.market_fragment_market_depth;
    }

    @Override
    protected void initArguments(Bundle savedInstanceState) {
        super.initArguments(savedInstanceState);
        pair = getArguments().getString(IntentConstant.EXTRA_PAIR);
    }

    @Override
    protected void initView() {
        mAxisColor = ContextCompat.getColor(getContext(), R.color.color_gray_dark);

        mChartDepth.setScaleEnabled(false);
        mChartDepth.setDrawBorders(false);
        mChartDepth.setDragEnabled(true);
        mChartDepth.setAutoScaleMinMaxEnabled(true);
        mChartDepth.setHighlightPerDragEnabled(false);
        mChartDepth.setScaleYEnabled(false);
        Legend lineChartLegend = mChartDepth.getLegend();
        lineChartLegend.setEnabled(false);
        mChartDepth.getDescription().setEnabled(false);

        XAxis xAxis = mChartDepth.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setLabelCount(3, true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                if (index >= 0 && index < mXAxisLabels.size()) {
                    return mXAxisLabels.get(index);
                }
                return "";
            }
        });

        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextSize(8);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.color_gray_dark));

        YAxis axisLeft = mChartDepth.getAxisLeft();
        axisLeft.setEnabled(false);

        YAxis axisRight = mChartDepth.getAxisRight();
        axisRight.setLabelCount(5);
        axisRight.setDrawLabels(true);
        axisRight.setTextColor(mAxisColor);
        axisRight.setTextSize(AXIS_RIGHT_TEXT_SIZE);
        axisRight.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisRight.setDrawAxisLine(false);
        axisRight.setDrawGridLines(false);
        axisRight.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return DoubleUtil.formatValue(value);
            }
        });
        axisRight.setSpaceBottom(0);

        mChartDepth.setDoubleTapToZoomEnabled(false);

        mChartDepth.setOnTouchListener(new ChartInfoViewHandler(mChartDepth));

        mChartDepth.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int chartWidth = mChartDepth.getWidth();
                int width = mLlInfoView.getWidth();
                float posX = h.getXPx();
                int offset = 10;

                float marginLeft = posX + offset;
                if (marginLeft < 0) {
                    marginLeft = -1 * (marginLeft);
                } else if (marginLeft + width > chartWidth - 50) {
                    marginLeft = posX - width - offset;
                }

                float posY = h.getYPx();
                float marginTop = posY + offset;
                int chartHeight = mChartDepth.getHeight() - 40;
                int height = mLlInfoView.getHeight();
                if (marginTop < 0) {
                    marginTop = -1 * (marginTop);
                } else if (marginTop + height > chartHeight) {
                    marginTop = posY - height - offset;
                }

                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mLlInfoView.getLayoutParams();
                lp.leftMargin = (int) marginLeft;
                lp.topMargin = (int) marginTop;
                mLlInfoView.setLayoutParams(lp);

                mLlInfoView.setVisibility(View.VISIBLE);
                setInfoViewData(e);
            }

            @Override
            public void onNothingSelected() {
                mLlInfoView.setVisibility(View.GONE);
                mChartDepth.highlightValue(null);
            }
        });
    }

    private void setInfoViewData(Entry e) {
        if (e == null || mTvPrice == null || mTvVol == null) {
            return;
        }

        int index = (int) e.getX();
        if (index >= 0 && index < mXAxisLabels.size()) {
            mTvPrice.setText(getString(R.string.market_depth_price, getUnit(), mXAxisLabels.get(index)));
        }

        mTvVol.setText(getString(R.string.market_depth_vol, DoubleUtil.formatValue(e.getY()) + ""));
    }

    private String getUnit() {
        if (pair != null && pair.contains("/")) {
            return pair.substring(pair.indexOf("/") + 1);
        }
        return "";
    }

    @Override
    protected void initData() {
        initDataSet();
        String symbol = "123092:BTC/ETH";
        mPresenter.getDepthData(symbol);
    }

    private void initDataSet() {
        ArrayList<Entry> valuesLeft = new ArrayList<>();
        mLineDataSetLeft = new LineDataSet(valuesLeft, "buy data set");
        setLineDataSet(mLineDataSetLeft);
        mLineDataSetLeft.setColor(ContextCompat.getColor(getContext(), R.color.color_green_light));
        mLineDataSetLeft.setFillColor(ContextCompat.getColor(getContext(), R.color.color_green_light_alpha_10));

        ArrayList<Entry> valuesRight = new ArrayList<>();
        mLineDataSetRight = new LineDataSet(valuesRight, "sell data set");
        setLineDataSet(mLineDataSetRight);
        mLineDataSetRight.setColor(ContextCompat.getColor(getContext(), R.color.color_red_light));
        mLineDataSetRight.setFillColor(ContextCompat.getColor(getContext(), R.color.color_red_light_alpha_10));

        LineData lineData = new LineData(mLineDataSetLeft, mLineDataSetRight);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        mChartDepth.setData(combinedData);
    }

    private void setLineDataSet(LineDataSet lineDataSet) {
        lineDataSet.setDrawIcons(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setCircleColor(Color.TRANSPARENT);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(1f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setValueTextSize(9f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setHighlightLineWidth(0.5f);
        lineDataSet.setHighLightColor(ContextCompat.getColor(getContext(), R.color.chart_highlight_color));
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
    }

    private void setData(List<DepthBean> buyBillList, List<DepthBean> sellBillList) {
        if (mLineDataSetLeft == null || mLineDataSetRight == null) {
            initDataSet();
        }

        mLineDataSetLeft.clear();
        mLineDataSetRight.clear();
        mXAxisLabels.clear();

        DepthBean bean = null;
        for (int i = 0; i < buyBillList.size(); i++) {
            bean = buyBillList.get(i);
            mLineDataSetLeft.addEntry(new Entry(i, bean.getAmount().floatValue(), bean));
            mXAxisLabels.add(bean.getPrice().toPlainString());
        }

        int startIndex = buyBillList.size();
        for (int i = 0; i < sellBillList.size(); i++) {
            bean = sellBillList.get(i);
            mLineDataSetRight.addEntry(new Entry(i + startIndex, bean.getAmount().floatValue(), bean));
            mXAxisLabels.add(bean.getPrice().toPlainString());
        }

        mLineDataSetLeft.notifyDataSetChanged();
        mLineDataSetRight.notifyDataSetChanged();
        mChartDepth.getData().notifyDataChanged();
        mChartDepth.notifyDataSetChanged();
        mChartDepth.invalidate();

    }

    @Override
    public void showDepth(List<DepthBean> buyBillList, List<DepthBean> sellBillList) {
        setData(buyBillList, sellBillList);
    }
}
