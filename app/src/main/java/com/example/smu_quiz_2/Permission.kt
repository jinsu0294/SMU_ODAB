package com.example.smu_quiz_2

import android.content.Context
import android.preference.PreferenceManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.util.ArrayList

class Permission (context: Context){
    var context = context
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    val edit = pref.edit()

    var permissionlistener:PermissionListener = object :PermissionListener{

        override fun onPermissionGranted() {
            // 권한 허가시 실행 할 내용
            // 쉐어프리퍼런스 사용하여 key: isPermission -> true
            edit.putBoolean("isPermission",true).apply()
            edit.commit()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            // 권한 거부시 실행 할 내용
            // 쉐어프리퍼런스 사용하여 key: isPermission -> false
            edit.putBoolean("isPermission",false).apply()
            edit.commit()
        }
    }

    fun checkPer(){
        TedPermission.with(context)
            .setPermissionListener(permissionlistener)
            .setRationaleMessage(R.string.need_permission)
            .setDeniedMessage(R.string.way_permission)
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA)
            .check()
    }


}