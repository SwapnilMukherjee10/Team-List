package com.mustang.teamlist.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mustang.teamlist.Model.TeamMember
import com.mustang.teamlist.R
import com.mustang.teamlist.View.MainActivity
import com.mustang.teamlist.View.UpdateActivity

class TeamAdapter(private val activity: MainActivity) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    var teamMembers : List<TeamMember> = ArrayList()

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewName : TextView = itemView.findViewById(R.id.textViewName)
        val textViewNumber : TextView = itemView.findViewById(R.id.textViewNumber)
        val textViewEmail : TextView = itemView.findViewById(R.id.textViewEmail)
        val textViewAddress : TextView = itemView.findViewById(R.id.textViewAddress)
        val cardView : CardView = itemView.findViewById(R.id.cardView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {

        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.team_item,parent,false)

        return TeamViewHolder(view)

    }

    override fun getItemCount(): Int {
        return teamMembers.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {

        val currentTeamMember : TeamMember = teamMembers[position]

        holder.textViewName.text = currentTeamMember.memberName
        holder.textViewNumber.text = currentTeamMember.contact
        holder.textViewEmail.text = currentTeamMember.email
        holder.textViewAddress.text = currentTeamMember.address

        holder.cardView.setOnClickListener {

            val intent = Intent(activity,UpdateActivity::class.java)
            intent.putExtra("currentName",currentTeamMember.memberName)
            intent.putExtra("currentNumber",currentTeamMember.contact)
            intent.putExtra("currentEmail",currentTeamMember.email)
            intent.putExtra("currentAddress",currentTeamMember.address)
            intent.putExtra("currentId",currentTeamMember.id)

            activity.updateActivityResultLauncher.launch(intent)

        }

    }

    fun setMember(members: List<TeamMember>) {

        this.teamMembers = members
        notifyDataSetChanged()

    }

    fun getMember(position: Int) : TeamMember{

        return teamMembers[position]

    }

}