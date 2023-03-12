package com.mustang.teamlist.View

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.mustang.teamlist.Adapters.TeamAdapter
import com.mustang.teamlist.Model.TeamMember
import com.mustang.teamlist.R
import com.mustang.teamlist.TeamApplication
import com.mustang.teamlist.ViewModel.TeamViewModel
import com.mustang.teamlist.ViewModel.TeamViewModelFactory
import com.mustang.teamlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var teamViewModel: TeamViewModel

    lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val teamAdapter = TeamAdapter(this)
        recyclerView.adapter = teamAdapter

        registerActivityResultLauncher()

        val viewModelFactory = TeamViewModelFactory((application as TeamApplication).repository)

        teamViewModel = ViewModelProvider(this,viewModelFactory).get(TeamViewModel::class.java)

        teamViewModel.allMembers.observe(this, Observer {teamMembers->

            //update UI
            teamAdapter.setMember(teamMembers)

        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                teamViewModel.delete(teamAdapter.getMember(viewHolder.adapterPosition))
                Toast.makeText(applicationContext, "Member details deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(recyclerView)

    }

    private fun registerActivityResultLauncher() {

        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
                                    , ActivityResultCallback {resultAddMember ->
                val resultCode = resultAddMember.resultCode
                val data = resultAddMember.data

                if (resultCode == RESULT_OK && data != null) {
                    val memberName : String = data.getStringExtra("memberName").toString()
                    val memberNumber : String = data.getStringExtra("memberNumber").toString()
                    val memberEmail : String = data.getStringExtra("memberEmail").toString()
                    val memberAddress : String = data.getStringExtra("memberAddress").toString()

                    val memberDetails = TeamMember(memberName,memberEmail,memberAddress,memberNumber)
                    teamViewModel.insert(memberDetails)
                }
            })

        updateActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
            , ActivityResultCallback {resultUpdateMember ->
                val resultCode = resultUpdateMember.resultCode
                val data = resultUpdateMember.data

                if (resultCode == RESULT_OK && data != null) {
                    val updatedName: String = data.getStringExtra("updatedName").toString()
                    val updatedNumber: String = data.getStringExtra("updatedNumber").toString()
                    val updatedEmail: String = data.getStringExtra("updatedEmail").toString()
                    val updatedAddress: String = data.getStringExtra("updatedAddress").toString()
                    val memberId = data.getIntExtra("memberId",-1)

                    val newMember = TeamMember(updatedName,updatedEmail,updatedAddress,updatedNumber)
                    newMember.id = memberId

                    teamViewModel.update(newMember)
                }
            })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

       when (item.itemId) {
           R.id.item_add_member -> {
               val intent = Intent(this@MainActivity,AddMemberActivity::class.java)
               addActivityResultLauncher.launch(intent)
           }
           R.id.item_delete_all_members -> showDialogMessage()
           R.id.item_logout -> {
               FirebaseAuth.getInstance().signOut()
               val intent = Intent(this,LoginActivity::class.java)
               startActivity(intent)
               Toast.makeText(applicationContext, "You have been logged out", Toast.LENGTH_SHORT).show()
               finish()
           }
       }
        return true

    }
    private fun showDialogMessage() {

        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete every member info?")
        dialogMessage.setMessage("If clicked Yes every member info will be deleted, if you want to delete a specific member info," +
                " please swipe left or right")
        dialogMessage.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        dialogMessage.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            teamViewModel.deleteAllMembers()
        })
        dialogMessage.create().show()

    }

}