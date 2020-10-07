package ragalik.baraxolka.utils

import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import coil.load
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.view.ui.activity.AdministratorActivity
import ragalik.baraxolka.view.ui.activity.SettingsActivity
import ragalik.baraxolka.view.ui.fragment.*

class AppDrawer {

    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mCurrentProfile: ProfileDrawerItem

    fun create() {
        initLoader()
        createHeader()
        createDrawer()
        mDrawerLayout = mDrawer.drawerLayout
    }

    private fun initLoader() {
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                //imageView.downloadAndSetImage(uri.toString())
            }
        })
    }

//    fun updateHeader() {
//        mCurrentProfile
//                .withName(USER.fullname)
//                .withEmail(USER.phone)
//                .withIcon(USER.photoUrl)
//
//        mHeader.updateProfile(mCurrentProfile)
//    }

    fun disableDrawer() {
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }

    fun enableDrawer() {
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            mDrawer.openDrawer()
        }
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
                .withActivity(APP_ACTIVITY)
                .withToolbar(APP_ACTIVITY.mToolbar)
                .withActionBarDrawerToggle(true)
                .withSelectedItem(-1)
                .withAccountHeader(mHeader)
                .addDrawerItems(
                        PrimaryDrawerItem().withIdentifier(100)
                                .withIconTintingEnabled(true)
                                .withName("Все объявления")
                                .withSelectable(false)
                                .withIcon(R.drawable.ic_ads),

                        DividerDrawerItem(),

                        PrimaryDrawerItem().withIdentifier(101)
                                .withIconTintingEnabled(true)
                                .withName("Сообщения")
                                .withSelectable(false)
                                .withIcon(R.drawable.ic_messages),

                        PrimaryDrawerItem().withIdentifier(102)
                                .withIconTintingEnabled(true)
                                .withName("Мои объявления")
                                .withSelectable(false)
                                .withIcon(R.drawable.ic_my_ads),

                        PrimaryDrawerItem().withIdentifier(103)
                                .withIconTintingEnabled(true)
                                .withName("Закладки")
                                .withSelectable(false)
                                .withIcon(R.drawable.ic_bookmark),

                        PrimaryDrawerItem().withIdentifier(104)
                                .withIconTintingEnabled(true)
                                .withName("Техническая поддержка")
                                .withSelectable(false)
                                .withIcon(R.drawable.ic_build_black_24dp),

                        DividerDrawerItem(),

                        PrimaryDrawerItem().withIdentifier(105)
                                .withIconTintingEnabled(true)
                                .withName("Рассмотрение")
                                .withBadge("123")
                                .withSelectable(false),

                        PrimaryDrawerItem().withIdentifier(106)
                                .withIconTintingEnabled(true)
                                .withName("Администрирование")
                                .withSelectable(false)

                ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                    override fun onItemClick(
                            view: View?,
                            position: Int,
                            drawerItem: IDrawerItem<*>
                    ): Boolean {
                        clickToItem(position)
                        return false
                    }
                })
                .addStickyDrawerItems(

                        PrimaryDrawerItem().withIdentifier(110)
                                .withIconTintingEnabled(true)
                                .withName("Настройки")
                                .withSelectable(false)
                                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                                        APP_ACTIVITY.replaceActivity(SettingsActivity())
                                        return false
                                    }
                                })
                                .withIcon(R.drawable.ic_settings_black_24dp)

                )
                .withStickyFooterShadow(false)
                .withStickyFooterDivider(true)
                .build()
    }

    private fun clickToItem(position: Int) {
        // showToast(position.toString())
        when (position) {
            4 -> APP_ACTIVITY.replaceFragment(MyAdsFragment())
            5 -> APP_ACTIVITY.replaceFragment(FavouritesFragment())
            6 -> APP_ACTIVITY.replaceFragment(TechnicalSupportFragment())
            8 -> APP_ACTIVITY.replaceFragment(AdModeratorFragment())
            9 -> APP_ACTIVITY.replaceActivity(AdministratorActivity())
        }
    }

    private fun createHeader() {
        mCurrentProfile = ProfileDrawerItem()
                .withName("Vlad Telebaev")
                .withEmail("+375 (29) 888-88-88")
                .withIcon(R.drawable.girls)
                .withIdentifier(200)

        mHeader = AccountHeaderBuilder()
                .withActivity(MainActivity.activity)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(mCurrentProfile)
                .withOnAccountHeaderProfileImageListener(object : AccountHeader.OnAccountHeaderProfileImageListener {
                    override fun onProfileImageClick(view: View, profile: IProfile<*>, current: Boolean): Boolean {
                        APP_ACTIVITY.replaceFragment(AccountFragment())
                        return false
                    }

                    override fun onProfileImageLongClick(view: View, profile: IProfile<*>, current: Boolean): Boolean {
                        return false
                    }
                }).build()


        mHeader.headerBackgroundView.load(R.drawable.girls) {
            transformations(BlurTransformation(MainActivity.activity, 25f, 1f))
        }
    }

}