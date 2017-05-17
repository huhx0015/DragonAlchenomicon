package com.huhx0015.dragonalchenomicon.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.model.contracts.IngredientPickerAdapterContract;
import com.huhx0015.dragonalchenomicon.view.listeners.IngredientPickerAdapterListener;
import com.huhx0015.dragonalchenomicon.presenters.IngredientPickerAdapterPresenter;
import com.huhx0015.dragonalchenomicon.utils.AlchenomiconImageUtils;
import java.util.HashSet;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michael Yoon Huh on 5/14/2017.
 */

public class IngredientPickerDialogAdapter extends RecyclerView.Adapter<IngredientPickerDialogAdapter.IngredientPickerViewHolder>
        implements IngredientPickerAdapterContract.View {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ADAPTER VARIABLES:
    private Context mContext;

    // LISTENER VARIABLES:
    private IngredientPickerAdapterListener mListener;

    // PRESENTER VARIABLES:
    private IngredientPickerAdapterContract.Presenter mPresenter;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public IngredientPickerDialogAdapter(HashSet<String> ingredientList,
                                         IngredientPickerAdapterListener listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
        setPresenter(new IngredientPickerAdapterPresenter(this)); // Sets the presenter for this adapter.
        mPresenter.setIngredientList(ingredientList);
    }

    /** ADAPTER METHODS ________________________________________________________________________ **/

    @Override
    public IngredientPickerDialogAdapter.IngredientPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ingredient_list, parent, false);
        return new IngredientPickerDialogAdapter.IngredientPickerViewHolder(view, new IngredientPickerViewHolder.IngredientPickerClickListener() {
            @Override
            public void onIngredientClicked(View view, int position) {
                mPresenter.onIngredientClicked(position);
            }
        });
    }

    @Override
    public void onBindViewHolder(IngredientPickerDialogAdapter.IngredientPickerViewHolder holder, int position) {
        mPresenter.setIngredientRow(holder, position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mPresenter.getIngredientCount();
    }

    @Override
    public void onViewRecycled(IngredientPickerDialogAdapter.IngredientPickerViewHolder holder) {
        super.onViewRecycled(holder);
        mPresenter.clearIngredientImage(holder.ingredientImage);
    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    @Override
    public void setPresenter(IngredientPickerAdapterContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showIngredientRow(IngredientPickerViewHolder holder, String ingredient) {

        // INGREDIENT IMAGE:
        int ingredientResource = AlchenomiconImageUtils.getItemImage(ingredient);
        mPresenter.setIngredientImage(ingredientResource, holder.ingredientImage);

        // INGREDIENT NAME:
        mPresenter.setIngredientName(ingredient, holder.ingredientName);
    }

    @Override
    public void showIngredientName(String ingredient, TextView view) {
        view.setText(ingredient);
    }

    @Override
    public void showIngredientImage(int resource, ImageView view) {
        Glide.with(mContext)
                .load(resource)
                .fitCenter()
                .into(view);
    }

    @Override
    public void dismissIngredientPicker(String ingredient) {
        mListener.onIngredientClicked(ingredient);
    }

    @Override
    public void clearIngredientImage(ImageView view) {
        Glide.clear(view);
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    public static class IngredientPickerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private IngredientPickerClickListener ingredientPickerClickListener;

        @BindView(R.id.adapter_ingredient_image) ImageView ingredientImage;
        @BindView(R.id.adapter_ingredient_list_layout) RelativeLayout ingredientContainer;
        @BindView(R.id.adapter_ingredient_name) TextView ingredientName;

        IngredientPickerViewHolder(View itemView, IngredientPickerClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (listener != null) {
                ingredientPickerClickListener = listener;
                ingredientContainer.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            int itemPos = getAdapterPosition();
            ingredientPickerClickListener.onIngredientClicked(v, itemPos);
        }

        interface IngredientPickerClickListener {
            void onIngredientClicked(View view, int position);
        }
    }
}