package com.mustang.teamlist.ViewModel

import androidx.lifecycle.*
import com.mustang.teamlist.Model.TeamMember
import com.mustang.teamlist.Repository.TeamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamViewModel(private val repository: TeamRepository) : ViewModel() {

    val allMembers : LiveData<List<TeamMember>> = repository.allMembers.asLiveData()

    fun insert(teamMember: TeamMember) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(teamMember)
    }

    fun update(teamMember: TeamMember) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(teamMember)
    }

    fun delete(teamMember: TeamMember) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(teamMember)
    }

    fun deleteAllMembers() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllMembers()
    }

}

class TeamViewModelFactory(private var repository: TeamRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            return TeamViewModel(repository) as T
        } else {
            throw IllegalArgumentException("unknown View Model")
        }
    }
}