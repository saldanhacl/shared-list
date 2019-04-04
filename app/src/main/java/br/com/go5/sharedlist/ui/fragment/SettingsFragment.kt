package br.com.go5.sharedlist.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.persistence.UserInfo
import br.com.go5.sharedlist.ui.activity.LoginActivity
import br.com.go5.sharedlist.ui.activity.MainActivity
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import org.jetbrains.anko.intentFor

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setFragmentTitle()
        return AboutPage(activity!!)
            .isRTL(false)
            .setImage(R.mipmap.ic_launcher)
            .setDescription("Aplicativo feito para a disciplina de Trabalho Interdisciplinar de Software. Desenvolvido pelos alunos do 5° período do curso de Engenharia de Software da Puc Minas")
            .addItem(Element()
                .setTitle("Sair")
                .setOnClickListener {
                    signOut()
                })
            .create()
    }

    private fun signOut() {
        UserInfo.isLogged = false
        UserInfo.username = ""
        startActivity(activity?.intentFor<LoginActivity>())
    }

    private fun setFragmentTitle() {
        val mainActivity = activity as MainActivity
        val title = mainActivity.getString(R.string.fragment_title_settings)
        mainActivity.actionBar.title = title
    }

}
