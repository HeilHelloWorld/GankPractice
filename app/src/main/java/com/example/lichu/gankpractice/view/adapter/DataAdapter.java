package com.example.lichu.gankpractice.view.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.camnter.easyrecyclerview.adapter.EasyRecyclerViewAdapter;
import com.camnter.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.example.lichu.gankpractice.R;
import com.example.lichu.gankpractice.constant.Constants;
import com.example.lichu.gankpractice.constant.UrlMatch;
import com.example.lichu.gankpractice.enity.BasicGankBean;
import com.example.lichu.gankpractice.enity.DailyGankBean;
import com.example.lichu.gankpractice.enity.GankBean;
import com.example.lichu.gankpractice.gank.GankApi;
import com.example.lichu.gankpractice.gank.GankType;
import com.example.lichu.gankpractice.utils.DateUtil;
import com.example.lichu.gankpractice.utils.GlideUtil;
import com.example.lichu.gankpractice.widget.RatioImageView;
import com.squareup.okhttp.internal.RouteDatabase;

import org.w3c.dom.Text;

/**
 * Usage:
 * Created: lichu on 2016/9/5.
 */
public class DataAdapter extends EasyRecyclerViewAdapter{

    private static final int LAYOUT_DAILY = 0;
    private static final int LAYOUT_CATEGORY = 1;
    private static final int LAYOUT_WELFARE = 2;

    private Context mContext;
    private int type;
    private OnClickListener mListener;



    @Override
    public int[] getItemLayouts() {
        return new int[] {R.layout.item_daily, R.layout.item_category, R.layout.item_welfare};
    }

    @Override
    public void onBindRecycleViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        switch (this.getRecycleViewItemType(position)) {
            case LAYOUT_DAILY:
                this.loadingDaily(viewHolder, position);
                break;
            case LAYOUT_CATEGORY:
                this.loadingCategory(viewHolder, position);
                break;
            case LAYOUT_WELFARE:
                this.loadingWelfare(viewHolder, position);
                break;
        }
    }



    public void loadingCategory(EasyRecyclerViewHolder viewHolder, int position) {
        BasicGankBean basicGankBean = this.getItem(position);
        TextView dataTitle = viewHolder.findViewById(R.id.data_title_tv);
        TextView dataDate = viewHolder.findViewById(R.id.data_date_tv);
        TextView dataVia = viewHolder.findViewById(R.id.data_via_tv);
        TextView dataTag = viewHolder.findViewById(R.id.data_tag_tv);

        if (TextUtils.isEmpty(basicGankBean.desc)) {
            dataTitle.setText("");
        } else {
            dataTitle.setText(basicGankBean.desc.trim());
        }

        if (basicGankBean.publishedAt == null) {
            dataDate.setText("");
        } else {
            dataDate.setText(DateUtil.getTimestampString(basicGankBean.publishedAt));
        }

        if (TextUtils.isEmpty(basicGankBean.who)) {
            dataVia.setText("");
        } else {
            dataVia.setText(this.mContext.getString(R.string.via, basicGankBean.who));
        }

        if (TextUtils.isEmpty(basicGankBean.url)) {
            dataTag.setVisibility(View.GONE);
        } else {
            this.setTag(dataTag, basicGankBean.url);
        }
    }

    private void setTag(TextView dataTag, String url) {
        String key = UrlMatch.processUrl(url);
        GradientDrawable drawable = (GradientDrawable) dataTag.getBackground();

        if (UrlMatch.url2Content.containsKey(key)) {
            drawable.setColor(UrlMatch.url2Color.get(key));
            dataTag.setText(UrlMatch.url2Content.get(key));
        } else {
            if (this.type == GankType.VIDEO) {
                drawable.setColor(UrlMatch.OTHER_VIDEO_COLOR);
                dataTag.setText(UrlMatch.OTHER_VIDEO_CONTENT);
            } else {
                if (url.contains(UrlMatch.GITHUB_PREFIX)) {
                    drawable.setColor(UrlMatch.url2Color.get(UrlMatch.GITHUB_PREFIX));
                    dataTag.setText(UrlMatch.url2Content.get(UrlMatch.GITHUB_PREFIX));
                } else {
                    drawable.setColor(UrlMatch.OTHER_BLOG_COLOR);
                    dataTag.setText(UrlMatch.OTHER_BLOG_CONTENT);
                }
            }
        }
    }

    public void loadingDaily(EasyRecyclerViewHolder viewHolder, int position) {
        DailyGankBean dailyGankBean = this.getItem(position);
        if (dailyGankBean == null) {
            return;
        }
        ImageView dailyWelfare = viewHolder.findViewById(R.id.daily_welfare);
        TextView dailyTitle = viewHolder.findViewById(R.id.daily_title);
        TextView dailyDate = viewHolder.findViewById(R.id.daily_date);
        TextView tagAndroid = viewHolder.findViewById(R.id.daily_type_android);
        TextView tagIOS = viewHolder.findViewById(R.id.daily_type_ios);
        TextView tagJs =  viewHolder.findViewById(R.id.daily_type_js);

        if (dailyGankBean.results.videoData != null && dailyGankBean.results.videoData.size() > 0) {
            BasicGankBean videoBean = dailyGankBean.results.videoData.get(0);
            dailyTitle.setText(videoBean.desc.trim());
            dailyDate.setText(DateUtil.parseDateToString(videoBean.publishedAt.getTime(), Constants.DAILY_DATE_FORMAT));
        } else if (dailyGankBean.results.welfareData != null && dailyGankBean.results.welfareData.size() > 0) {
            BasicGankBean welfareBean = dailyGankBean.results.welfareData.get(0);
            dailyTitle.setText(welfareBean.desc.trim());
            dailyDate.setText(DateUtil.parseDateToString(welfareBean.publishedAt.getTime(), Constants.DAILY_DATE_FORMAT));
        } else {
            dailyTitle.setText("福利不见了。。");
            dailyDate.setText("二斤锟斤拷");
        }

        if (dailyGankBean.results.welfareData != null && dailyGankBean.results.welfareData.size() > 0) {
            BasicGankBean welfareBean = dailyGankBean.results.welfareData.get(0);
            GlideUtil.display(dailyWelfare, welfareBean.url);
            dailyWelfare.setOnClickListener(view -> {
                if (DataAdapter.this.mListener != null) {
                    DataAdapter.this.mListener.onClickPicture(welfareBean.url, welfareBean.desc, view);
                }
            });
        } else {
            GlideUtil.displayNative(dailyWelfare, R.mipmap.ic_launcher);
        }

        if (dailyGankBean.category == null) {
            tagAndroid.setVisibility(View.GONE);
            tagIOS.setVisibility(View.GONE);
            tagJs.setVisibility(View.GONE);
        } else {
            if (dailyGankBean.category.contains(GankApi.DATA_TYPE_ANDROID)) {
                tagAndroid.setVisibility(View.VISIBLE);
            } else {
                tagAndroid.setVisibility(View.GONE);
            }

            if (dailyGankBean.category.contains(GankApi.DATA_TYPE_IOS)) {
                tagIOS.setVisibility(View.VISIBLE);
            } else {
                tagIOS.setVisibility(View.GONE);
            }

            if (dailyGankBean.category.contains(GankApi.DATA_TYPE_JS)) {
                tagJs.setVisibility(View.VISIBLE);
            } else {
                tagJs.setVisibility(View.GONE);
            }
        }
    }

    public void loadingWelfare(EasyRecyclerViewHolder viewHolder, int position) {
        BasicGankBean basicGankBean = this.getItem(position);
        if (basicGankBean == null) {
            return;
        }

        RatioImageView welfareImg = viewHolder.findViewById(R.id.welfare_iv);

        if (position%2 == 0) {
            welfareImg.setImageRatio(0.7f);
        } else {
            welfareImg.setImageRatio(0.6f);
        }

        if (TextUtils.isEmpty(basicGankBean.url)) {
            GlideUtil.displayNative(welfareImg, R.mipmap.ic_launcher);
        } else {
            GlideUtil.display(welfareImg, basicGankBean.url);
        }
    }

    @Override
    public int getRecycleViewItemType(int position) {
        switch (this.type) {
            case GankType.DAILY:
                return LAYOUT_DAILY;
            case GankType.ANDROID:
            case GankType.IOS:
            case GankType.JS:
            case GankType.RESOURCES:
            case GankType.VIDEO:
            case GankType.APP:
                return LAYOUT_CATEGORY;
            case GankType.WELFRAE:
                return LAYOUT_WELFARE;
            default:
                return LAYOUT_DAILY;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public interface OnClickListener {
        void onClickPicture(String url, String desc, View view);
    }

    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }
}
