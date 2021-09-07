package fi.metropolia.kari.naamat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

lateinit var frag1: Frag1
lateinit var frag2: Frag2
lateinit var frag3: Frag3
lateinit var fTransaction: FragmentTransaction
lateinit var fManager: FragmentManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1.setOnClickListener{swapFrags(1)}
        btn2.setOnClickListener{swapFrags(2)}
        frag1 = Frag1()
        frag2 = Frag2()
        frag3 = Frag3()
        fManager = supportFragmentManager
        fTransaction = fManager.beginTransaction()
        fTransaction.add(R.id.fcontainer, frag1)
        fTransaction.commit()

    }

    private fun swapFrags(num: Int){
        if(num == 1){
            fTransaction = fManager.beginTransaction()
            fTransaction.replace(R.id.fcontainer, frag2)
            fTransaction.addToBackStack(null)
            fTransaction.commit()
        } else{
            fTransaction = fManager.beginTransaction()
            fTransaction.replace(R.id.fcontainer, frag3)
            fTransaction.addToBackStack(null)
            fTransaction.commit()
        }
    }
}
