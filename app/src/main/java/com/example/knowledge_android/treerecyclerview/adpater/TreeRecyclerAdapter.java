package com.example.knowledge_android.treerecyclerview.adpater;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.knowledge_android.R;
import com.example.knowledge_android.treerecyclerview.tree.bean.TranHead;
import com.example.knowledge_android.treerecyclerview.view.TreeItem;
import com.example.knowledge_android.treerecyclerview.view.TreeItemGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



/**
 * @author Alex
 * @since 2021/09/12
 *
 * 树级结构RecyclerAdapter.
 * item之间有子父级关系,
 */
public class TreeRecyclerAdapter<T extends TreeItem> extends BaseRecyclerAdapter<T> {

    private TreeRecyclerViewType type;

    private ItemManager<T> mItemManager;
    /**
     * 最初的数据.没有经过增删操作.
     */
    private List<T> initialDatas;

    //@Override
    public void onBindViewHolderClick1(final TreeViewHolder holder) {
        if (!holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG","Click");
                    int layoutPosition = holder.getLayoutPosition();
                    //检查item的position,这个item是否可以点击
                    if (getCheckItem().checkPosition(layoutPosition)) {
                        //获得处理后的position
                        int itemPosition = getCheckItem().getAfterCheckingPosition(layoutPosition);
                        //拿到BaseItem
                        T item = getDatas().get(itemPosition);
                        //展开,折叠和item点击不应该同时响应事件.
                        //必须是TreeItemGroup才能展开折叠,并且type不能为 TreeRecyclerViewType.SHOW_ALL
                        if (type != TreeRecyclerViewType.SHOW_ALL && item instanceof TreeItemGroup) {
                            //展开,折叠
                            expandOrCollapse(((TreeItemGroup) item));
                        } else {
                            TreeItemGroup itemParentItem = item.getParentItem();
                            //判断上一级是否需要拦截这次事件，只处理当前item的上级，不关心上上级如何处理.
                            if (itemParentItem != null && itemParentItem.onInterceptClick(item)) {
                                return;
                            }
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(holder, getData(itemPosition), itemPosition);
                            } else {
                                //拿到对应item,回调.
                                getDatas().get(itemPosition).onClick();
                            }
                        }
                    }
                }
            });
        }
//        //添加长按事件
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                PosLog.info("长按事件触发");
//                //获得holder的position
//                int layoutPosition = holder.getLayoutPosition();
//                //检查position是否可以点击
//                if (getCheckItem().checkPosition(layoutPosition)) {
//                    //检查并得到真实的position
//                    int itemPosition = getCheckItem().getAfterCheckingPosition(layoutPosition);
//                    if (mOnItemLongClickListener != null) {
//                        return mOnItemLongClickListener.onItemLongClick(holder, getData(itemPosition), itemPosition);
//                    }
//                }
//                return true; //表示此事件已經被消費了，不會在觸發點擊事件
//            }
//        });
    }


    @Override
    public void onBindViewHolderClick(final TreeViewHolder holder) {

        Button refund = (Button) holder.itemView.findViewById(R.id.status);
        if (refund != null) {
            refund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG","退货状态点击");
                    int layoutPosition = holder.getLayoutPosition();
                    Log.i("TAG","布局位置:" + layoutPosition);
                    TranHead data = (TranHead) getItemManager().getItem(layoutPosition).getData();
                    Log.i("TAG","当前点击的交易数据:" + data.toString());
                    if (Objects.equals(data.getStatus(), "退货")) {
                        Log.i("TAG","点击退货");
//                        POSTerminalApplication application = POSTerminalApplication.getInstance();
//                        BeiRuiPosScreen posScreen = (BeiRuiPosScreen) application.getPosScreen();
//                        posScreen.showMessagePopup("退货",data.toString());
                    }
                }
            });
        }


        if (!holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG","click");
                    int layoutPosition = holder.getLayoutPosition();
                    //检查item的position,这个item是否可以点击
                    if (getCheckItem().checkPosition(layoutPosition)) {
                        //获得处理后的position
                        int itemPosition = getCheckItem().getAfterCheckingPosition(layoutPosition);
                        //拿到BaseItem
                        T item = getDatas().get(itemPosition);
                        //展开,折叠和item点击不应该同时响应事件.
                        //必须是TreeItemGroup才能展开折叠,并且type不能为 TreeRecyclerViewType.SHOW_ALL
                        if (type != TreeRecyclerViewType.SHOW_ALL && item instanceof TreeItemGroup) {
                            //展开,折叠
                            expandOrCollapse(((TreeItemGroup) item));
                        } else {
                            TreeItemGroup itemParentItem = item.getParentItem();
                            //判断上一级是否需要拦截这次事件，只处理当前item的上级，不关心上上级如何处理.
                            if (itemParentItem != null && itemParentItem.onInterceptClick(item)) {
                                return;
                            }
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(holder, getData(itemPosition), itemPosition);
                            } else {
                                //拿到对应item,回调.
                                getDatas().get(itemPosition).onClick();
                            }
                        }
                    }
                }
            });
        }
    }


    private boolean checkIsTreeItemGroup(T baseItem) {
        if (type != TreeRecyclerViewType.SHOW_ALL) {
            return true;
        } else if (baseItem instanceof TreeItemGroup) {
            return true;
        }
        return false;
    }

    /**
     * 获得初始的data
     *
     * @return
     */
    public List<T> getInitialDatas() {
        if (initialDatas == null) {
            initialDatas = getDatas();
        }
        return initialDatas;
    }

    @Override
    public void setDatas(List<T> datas) {
        Log.i("TAG",datas.toString());
        initialDatas = datas;
        if (type == TreeRecyclerViewType.SHOW_ALL) {
            for (int i = 0; i < datas.size(); i++) {
                T t = datas.get(i);
                getDatas().add(t);
                if (t instanceof TreeItemGroup) {
                    List childs = ((TreeItemGroup) t).getAllChilds();
                    if (childs != null) {
                        getDatas().addAll(childs);
                    }
                }
            }
        } else {
            super.setDatas(datas);
        }
    }


    public ItemManager<T> getItemManager() {
        if (mItemManager == null) {
            return new TreeItemManageImpl();
        }
        return mItemManager;
    }

    public void setItemManager(ItemManager itemManage) {
        this.mItemManager = itemManage;
    }

    /**
     * 相应RecyclerView的点击事件 展开或关闭某节点
     */
    private void expandOrCollapse(TreeItemGroup treeItemGroup) {
        boolean expand = treeItemGroup.isExpand();
        treeItemGroup.setExpand(!expand);
    }

    /**
     * 需要设置在setdata之前,否则type不会生效
     *
     * @param type
     */
    public void setType(TreeRecyclerViewType type) {
        this.type = type;
    }

    private class TreeItemManageImpl implements ItemManager<T> {
        @Override
        public void addItem(T item) {
            if (null == item) {
                return;
            }
            if (item instanceof TreeItemGroup) {
                getDatas().add(item);
            } else {
                TreeItemGroup itemParentItem = item.getParentItem();
                if (itemParentItem != null) {
                    List childs = itemParentItem.getChilds();
                    if (childs != null) {
                        int i = getDatas().indexOf(itemParentItem);
                        getDatas().add(i + itemParentItem.getChilds().size(), item);
                    } else {
                        childs = new ArrayList();
                        itemParentItem.setChilds(childs);
                    }
                    childs.add(item);
                }
            }
            notifyDataChanged();
        }

        @Override
        public void addItem(int position, T item) {
            getDatas().add(position, item);
            if (item != null && item.getParentItem() != null) {
                item.getParentItem().getChilds().add(item);
            }
            notifyDataChanged();
        }

        @Override
        public void addItems(List<T> items) {
            getDatas().addAll(items);
            notifyDataChanged();
        }

        @Override
        public void addItems(int position, List<T> items) {
            if (items == null) return;
            getDatas().addAll(position, items);
            notifyDataChanged();
        }

        @Override
        public void removeItem(T item) {
            if (null == item) {
                return;
            }
            getDatas().remove(item);

            TreeItemGroup itemParentItem = item.getParentItem();
            if (itemParentItem != null) {
                List childs = itemParentItem.getChilds();
                if (childs != null) {
                    childs.remove(item);
                }
            }
            notifyDataChanged();
        }

        @Override
        public void removeItem(int position) {
            T t = getDatas().get(position);
            TreeItemGroup parentItem = t.getParentItem();
            if (parentItem != null && parentItem.getChilds() != null) {
                parentItem.getChilds().remove(t);
            }
            getDatas().remove(position);
            notifyDataChanged();
        }

        @Override
        public void removeItems(List<T> items) {
            getDatas().removeAll(items);
            notifyDataChanged();
        }

        @Override
        public void replaceItem(int position, T item) {
            T t = getDatas().get(position);
            if (t instanceof TreeItemGroup) {
                getDatas().set(position, item);
            } else {
                TreeItemGroup parentItem = t.getParentItem();
                if (parentItem != null && parentItem.getChilds() != null) {
                    List childs = parentItem.getChilds();
                    int i = childs.indexOf(t);
                    childs.set(i, item);
                }
                getDatas().set(position, item);
            }
            notifyDataChanged();
        }

        @Override
        public T getItem(int position) {
            return getDatas().get(position);
        }

        @Override
        public void notifyDataChanged() {
            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(T item) {
            return getDatas().indexOf(item);
        }
    }
}
