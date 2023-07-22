package cn.haizhe.cat.widget.banner;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

import cn.haizhe.cat.R;

public class TvBannerAdapter extends BaseBannerAdapter<String> {

    @Override
    protected void bindData(BaseViewHolder<String> holder, String data, int position, int pageSize) {
        holder.itemView.setFocusable(false);
        holder.itemView.setFocusableInTouchMode(false);
        holder.itemView.setClickable(false);
        holder.itemView.setOnClickListener(null);
        ImageView imageView = holder.itemView.findViewById(R.id.iv_pic);
        if (imageView != null) {
            Glide.with(imageView)
                    .load(data)
                    .placeholder(R.drawable.bg_image_loading)
                    .error(R.drawable.bg_image_loading_err)
                    .into(imageView);
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_banner_pic;
    }

}
