package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var now = System.currentTimeMillis()
            textView.text = "버튼 클릭 : ${now}"
        }

        /*
        //안나온다. 해당 메인 쓰레드만 화면 처리가 가능한데 지금 메인쓰레드가 밑에 부분을 처리하느라 바쁘기 때문에 화면에 아무것도 안나오게 된다.
        while (true) {
            var now = System.currentTimeMillis()
            textView2.text = "무한 루프 : ${now}"
        }
         */
        isRunning = true
        var thread = ThreadClass1()
        thread.start()


    }

    //그래서 현재는 개발자가 쓰레드를 만들고 개발자가 스타트시키는 쓰래드안에서는 화면에 관한 처리를 할수 없다 해야 한다. -> 그래서 쓰래드 운영에 대한 학습이 필요하다.
    inner class ThreadClass1 : Thread() {
        override fun run() {
            //100밀리 세컨드 쉬겠다.
            while (isRunning) {
                SystemClock.sleep(100)
                var now = System.currentTimeMillis()
                Log.d("test1", "쓰레드 : ${now}")

                //안드로이드 오레오 8.0이상부터는 개발자가 발생시킨 쓰래드에서 화면 처리가 가능하다.
//                textView2.text = "쓰레드 : ${now}"
            }

        }
    }

    //쓰레드가 종료될때 호출 된다. 유저가 만든 쓰레드는 메인쓰레드가 사라져도 계속 존재하므로 메인 쓰레드가 끝날시 같이 종료시키기 위해 사용한다.
    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }
}
