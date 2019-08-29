package com.example.smu_quiz_2

import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.util.ArrayList

class Permission (context1: Context, isPsermission1:Boolean){
    var context = context1
    var isPermission = isPsermission1

    var permissionlistener:PermissionListener = object :PermissionListener{

        override fun onPermissionGranted() {
            //권한 허가시 실행 할 내용
            isPermission = true
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            // 권한 거부시 실행 할 내용
            isPermission = false
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