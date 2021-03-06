package com.jzsec.broker.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseLazyFragment;
import com.jzsec.broker.ui.market.SecuritiesMarketInfoActivity;
import com.jzsec.broker.ui.rxokgo.RxOkGoFragment;
import com.jzsec.broker.view.webview.TestWebViewActivity;
import com.thefinestartist.finestwebview.FinestWebView;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.SupportFragment;
import rx.functions.Action1;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 */
public class MessageFragment extends BaseLazyFragment {


    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        SupportFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        $(R.id.btn_open_market).setOnClickListener((v) -> {
            SecuritiesMarketInfoActivity.open(_mActivity);
        });

        $(R.id.btn_open_h5).setOnClickListener((v) -> {
            new FinestWebView.Builder(_mActivity).show("http://www.baidu.com");
        });

        _click2(R.id.tv_test_webview, (Object) -> {
            startActivity(new Intent(_mActivity, TestWebViewActivity.class));
        });

        _click(R.id.tv_RxJava_OkGO, (Void) -> {
            openMainFragment(RxOkGoFragment.newInstance());
        });

        _click2(R.id.tv_custom_calendar, (Object) ->{
            openMainFragment(CustomCalendarFragment.newInstance());
        });

        _click2(R.id.tv_list_container_layout, new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                openMainFragment(ListContainerFragment.newInstance());
            }
        });

        _click2(R.id.tv_test_draw_view, (Object) -> {
           openMainFragment(TestViewFragment.newInstance());
        });
    }
}
