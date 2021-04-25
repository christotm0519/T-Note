package com.example.t_note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import android.graphics.Path;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.example.t_note.Model.display;
import static com.example.t_note.Model.display.current_brush;
import static com.example.t_note.Model.display.colorList;
import static com.example.t_note.Model.display.pathList;
public class DibuixarActivity extends AppCompatActivity {
    public static Path path = new Path();
    public static Paint paint_brush = new Paint();
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageButton saveBtn;
    display drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dibuixar);
        saveBtn = (ImageButton) findViewById(R.id.guardar);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("tab1");
        arraylist.add("tab2");
        arraylist.add("tab3");
        prepareViewPager(viewPager,arraylist);
        tabLayout.setupWithViewPager(viewPager);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                save(view);
            }
        });
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arraylist) {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        BlankFragment fragment = new BlankFragment();
        for (int i=0;i<arraylist.size();i++){
            Bundle bundle = new Bundle();
            bundle.putString("title",arraylist.get(i));
            fragment.setArguments(bundle);
            adapter.addFragment(fragment,arraylist.get(i));
            fragment = new BlankFragment();
        }
        viewPager.setAdapter(adapter);
    }
    private class MainAdapter extends FragmentPagerAdapter {

        ArrayList<String> arraylist = new ArrayList<>();
        List<Fragment> fragmentList =  new ArrayList<>();

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        public void addFragment (Fragment fragment, String title){
            arraylist.add(title);
            fragmentList.add(fragment);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arraylist.get(position);
        }
    }

    public void eraser(View view){
        pathList.clear();
        colorList.clear();
        path.reset();
    }

    public void goma(View view){
        paint_brush.setColor(Color.WHITE);
        currentColor(paint_brush.getColor());
    }

    public void pencil(View view){
     paint_brush.setColor(Color.BLACK);
     currentColor(paint_brush.getColor());
    }
    public void redColor(View view){
        paint_brush.setColor(Color.RED);
        currentColor(paint_brush.getColor());
    }
    public void yelloColor(View view){
        paint_brush.setColor(Color.YELLOW);
        currentColor(paint_brush.getColor());
    }
    public void greenColor(View view){
        paint_brush.setColor(Color.GREEN);
        currentColor(paint_brush.getColor());

    }
    public void magentaColor(View view){
        paint_brush.setColor(Color.MAGENTA);
        currentColor(paint_brush.getColor());
    }
    public void blueColor(View view){
        paint_brush.setColor(Color.BLUE);
        currentColor(paint_brush.getColor());
    }
    public void currentColor(int c){
        current_brush = c;
        path = new Path();
    }
    public void save(View view){
        AlertDialog.Builder saveDialog = new AlertDialog.Builder ( this);
        saveDialog.setTitle ("Save?");
        saveDialog.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            @Override
                    public void onClick(DialogInterface dialog,int when){
                    drawingView.setDrawingCacheEnabled(true);
                    String imgSaved= MediaStore.Images.Media.insertImage(
                            getContentResolver(),drawingView.getDrawingCache(),
                            UUID.randomUUID().toString()+".png","drawing");
                    if (imgSaved!=null){
                        Toast savedToast = Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_LONG);
                        savedToast.show();
                    }else{
                        Toast unSaved = Toast.makeText(getApplicationContext(),"Opps, Error", Toast.LENGTH_LONG);
                        unSaved.show();
                    }
                    drawingView.destroyDrawingCache();

            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

}

