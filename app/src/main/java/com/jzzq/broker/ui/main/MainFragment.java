package com.jzzq.broker.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzzq.broker.R;
import com.jzzq.broker.base.BaseFragment;
import com.jzzq.broker.ui.contacts.ContactsFragment;
import com.jzzq.broker.ui.event.StartBrotherEvent;
import com.jzzq.broker.ui.event.TabSelectedEvent;
import com.jzzq.broker.ui.home.HomeFragment;
import com.jzzq.broker.ui.message.MessageFragment;
import com.jzzq.broker.ui.my.MyFragment;
import com.jzzq.broker.view.BottomBar;
import com.jzzq.broker.view.BottomBarTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 16/7/28.
 * e-mail: kangqiao610@gmail.com
 */
public class MainFragment extends BaseFragment {
    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = ContactsFragment.newInstance();
            mFragments[THIRD] = MessageFragment.newInstance();
            mFragments[FOURTH] = MyFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeFragment.class);
            mFragments[SECOND] = findFragment(ContactsFragment.class);
            mFragments[THIRD] = findFragment(MessageFragment.class);
            mFragments[FOURTH] = findFragment(MyFragment.class);
        }
        return view;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        mBottomBar.addItem(new BottomBarTab(_mActivity, R.mipmap.ic_bar_home, "首页"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_bar_contacter, "联系人"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_bar_message, "消息"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_bar_my, "我的"));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
                // 这里推荐使用EventBus来实现 -> 解耦 //后续使用RxBus实现.
                // 在MessageFragment, HomeFragment...中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {

        }
    }

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
