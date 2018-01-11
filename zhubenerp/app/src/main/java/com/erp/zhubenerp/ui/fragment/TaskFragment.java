package com.erp.zhubenerp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.erp.zhubenerp.R;
import com.erp.zhubenerp.adapter.TaskAdapter;
import com.erp.zhubenerp.app.ZhubenerpApp;
import com.erp.zhubenerp.base.NewBaseFragment;
import com.erp.zhubenerp.event.TaskCountEvent;
import com.erp.zhubenerp.global.Constant;
import com.erp.zhubenerp.model.WaitingItemWrapper;
import com.erp.zhubenerp.presenter.WaitItemPresenter;
import com.erp.zhubenerp.utils.LogUtil;
import com.erp.zhubenerp.utils.MD5Utils;
import com.erp.zhubenerp.utils.UIUtils;
import com.erp.zhubenerp.ui.activity.TaskDetailActivity;
import com.erp.zhubenerp.view.IWaitingItems;
import com.erp.zhubenerp.widget.EmptyView;
import com.erp.zhubenerp.widget.ErrorView;
import com.erp.zhubenerp.widget.RecycleViewDivider;
import com.erp.zhubenerp.widget.XRecyclerView;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gubin on 2018/1/9.
 */

@SuppressLint("ValidFragment")
public class TaskFragment extends NewBaseFragment implements OnItemClickListener, XRecyclerView.LoadingListener, IWaitingItems{
    @Bind(R.id.err_layout)
    ErrorView errLayout;
    @Bind(R.id.xrv)
    XRecyclerView xrv;
    private View rootView;
    private TaskAdapter adapter;
    private WaitItemPresenter presenter;
    private List<WaitingItemWrapper.WaitingItem> totalList;
    private int type;
    private int pageIndex = 1;

    private boolean isRefreshing = false;
    public TaskFragment(int type) {
        this.type = type;
        presenter = new WaitItemPresenter(this);
    }

    public TaskFragment() {

    }

    @Override
    protected void setView() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_task, container, false);
        ButterKnife.bind(this,rootView);
        xrv.setLayoutManager(new LinearLayoutManager(context));
        xrv.setRefreshProgressStyle(ProgressStyle.SysProgress);
        xrv.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xrv.addItemDecoration(new RecycleViewDivider(context,LinearLayoutManager.VERTICAL, 2,R.color.common_gray_dark));
        xrv.setLoadingListener(this);
        errLayout.setOnErrorListener(new ErrorView.OnErrorListener() {
            @Override
            public void onError() {
                onRefresh();
            }
        });
        EmptyView emptyView;
        if (type == 0)
            emptyView = new EmptyView(context, "暂无今日待办事项");
        else
            emptyView = new EmptyView(context, "暂无待办事项");
        ((ViewGroup) xrv.getParent()).addView(emptyView);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        xrv.setEmptyView(emptyView);
        adapter = new TaskAdapter(context, R.layout.item_task, totalList,xrv);
        xrv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        return rootView;
    }
/*
    @Override
    public void onItemClick(ViewGroup parent, View view, Object o, int position) {

    }
    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position){
        return false;
    }
    @Override
    protected void initData() {}*/

    @Override
    protected void initData() {
        totalList = new ArrayList<>();
        List<WaitingItemWrapper.WaitingItem> resultList = new LinkedList<WaitingItemWrapper.WaitingItem>();
        WaitingItemWrapper.WaitingItem data = new WaitingItemWrapper.WaitingItem();
        data.setCreateDate("20171208");
        data.setCreateDateStrHM("10:45");
        data.setCreateUser("admin");
        data.setCreateUserID(21);
        data.setCustomerId(186);
        data.setCustomerLevel("s");
        data.setCustomerName("amend");
        data.setFaliureReason("No");
        data.setFollowupId(216);
        resultList.add(data);
        totalList.addAll(resultList);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isRefreshing) {
            onRefresh();
        }
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        stopLoading();
        if (xrv == null) {
            xrv = (XRecyclerView) rootView.findViewById(R.id.xrv);
            errLayout = (ErrorView) rootView.findViewById(R.id.err_layout);
        }
        xrv.setVisibility(View.GONE);
        errLayout.setVisibility(View.VISIBLE);
        errLayout.setErrorText(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View view, Object o, int position) {
        WaitingItemWrapper.WaitingItem item = totalList.get(position);
        Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
        intent.putExtra("item", item);
        jump(intent);
    }

    @Override
    public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
        return false;
    }

    @Override
    public void onRefresh() {
        if (isRefreshing) {
            stopLoading();
            return;
        }
        pageIndex = 1;
        getData();
    }

    private void getData() {
        Map<String,String> params = new HashMap<>();
        if (type == 0) {
            params.put("op", "today");
        } else {
            params.put("op", "all");
        }

        params.put("pageIndex", String.valueOf(pageIndex));
        params.put("pageSize", String.valueOf(Constant.PAGECOUNT));
        params.put("TasksId",String.valueOf(2137));
        Map<String,Object> map = new HashMap<>();
        map.putAll(params);
        String sign = MD5Utils.getMD5Sign(map);
        params.put("sign", sign);
        LogUtil.e(TAG, UIUtils.getUrl(params));
        presenter.loadData(params);
 //       List<WaitingItemWrapper.WaitingItem> resultList = wrapper.getData();
 //       totalList.addAll(resultList);
    }

    @Override
    public void onLoadMore() {
        if (totalList.size() == 0) {
            xrv.loadMoreComplete();
            return;
        }
        if (totalList.size() > 0 && totalList.size() % Constant.PAGECOUNT != 0) {
            xrv.loadMoreComplete();
            return;
        }
        pageIndex++;
        getData();
    }

    private void stopLoading() {
        isRefreshing = false;
        if (xrv == null)
            return;
        if (pageIndex == 1)
            xrv.refreshComplete();
        else
            xrv.loadMoreComplete();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLoading();
    }

    @Override
    public void onSucceed(WaitingItemWrapper wrapper) {
        stopLoading();
        xrv.setVisibility(View.VISIBLE);
        errLayout.setVisibility(View.GONE);
//        List<WaitingItemWrapper.WaitingItem> resultList = wrapper.getData();
        List<WaitingItemWrapper.WaitingItem> resultList = new LinkedList<WaitingItemWrapper.WaitingItem>();
        WaitingItemWrapper.WaitingItem data = new WaitingItemWrapper.WaitingItem();
        data.setCreateDate("20171208");
        data.setCreateDateStrHM("10:45");
        data.setCreateUser("admin");
        data.setCreateUserID(21);
        data.setCustomerId(186);
        data.setCustomerLevel("s");
        data.setCustomerName("amend");
        data.setFaliureReason("No");
        data.setFollowupId(216);
        resultList.add(data);

        int allSum = wrapper.getAllSum();
        int todaySum = wrapper.getTodaySum();
        TaskCountEvent event = new TaskCountEvent(0,allSum, todaySum);
        EventBus.getDefault().post(event);
/*        if (pageIndex == 1) {
            totalList.clear();
        }

        if (resultList != null && resultList.size() > 0) {
            totalList.addAll(resultList);
        }
*/
        totalList.addAll(resultList);
        adapter.notifyDataSetChanged();
    }
}
