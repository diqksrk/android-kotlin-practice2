package com.example.a1permission

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    //사용자에게 권한을 활성화 할지 확인하고 활성화.
    var permission_list = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.WRITE_CONTACTS,
        android.Manifest.permission.READ_CONTACTS,
        android.Manifest.permission.SEND_SMS,
        android.Manifest.permission.RECEIVE_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()
    }

    fun checkPermission() {
        //마쉬멜로우 버전보다 낮으면 그냥 종료.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return
        }

        //일일히 확인 후 비활성화 되있으면 물어본다.
        for (permission : String in permission_list) {
            //권한 확인 후 활성화 되있냐 비활성화 되있냐 값이 넘어오게 됨.
            var chk = checkCallingOrSelfPermission(permission)

            //비활성화라면
            if (chk == PackageManager.PERMISSION_DENIED){
                requestPermissions(permission_list,0)
                break;
            }
        }
    }

    //물어보는 창 사라질때 이 메소드가 자동으로 호출 된다.
    //2번째 배열 -> 체크한 배열들의 리스트, 3번째 -> 체크한 리절트.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var idx = 0

        textView.text=""

        //indices는 index 번호가 하나씩 idx에 들어가게 된다.
        for (idx in grantResults.indices) {
            if (grantResults[idx] == PackageManager.PERMISSION_GRANTED){
                textView.append("${permission_list[idx]} : 허용함\n")
            }else {
                textView.append("${permission_list[idx]} : 허용하지 않음\n")
            }
        }
    }
}
