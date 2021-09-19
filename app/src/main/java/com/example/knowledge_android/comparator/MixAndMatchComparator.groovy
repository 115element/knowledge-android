package com.example.knowledge_android.comparator

//import hyi.cream.model.dac.MixAndMatchDac
//
//class MixAndMatchComparator implements Comparator {
//
//    /*******************排序,先排他,再按Type和Priority排序******************************************/
//    int compare(Object o1, Object o2) {
//        MixAndMatchDac m1 = (MixAndMatchDac) o1
//        MixAndMatchDac m2 = (MixAndMatchDac) o2
//        //先优先排他
//        int isexcludable1 = m1.isexcludable ? m1.isexcludable as int : 0
//        int isexcludable2 = m1.isexcludable ? m1.isexcludable as int : 0
//        //排序先type再priority
//        int type1 = m1.type ? m1.type as int : 0
//        int type2 = m2.type ? m2.type as int : 0
//
//        int priority1 = m1.priority ? m1.priority as int : 0
//        int priority2 = m2.priority ? m2.priority as int : 0
//
//        if (isexcludable1 == isexcludable2) {
//            if (type1 == type2) {
//                if (priority1 == priority2) {
//                    return -m1.getMmid().compareTo(m2.getMmid())
//                }
//                return priority1.compareTo(priority2) // priority1越小,越优先
//            }
//            return type1.compareTo(type2) // reverse order
//        }
//        int ret = isexcludable1.compareTo(isexcludable2) // reverse order
//        return ret
//    }
//    /*********************************************************************************************/
//    boolean equals(Object obj) {
//        return ((String) obj).equals(this)
//    }
//}
