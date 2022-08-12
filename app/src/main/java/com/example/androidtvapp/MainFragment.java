package com.example.androidtvapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.leanback.app.BrowseFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;

import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;

import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends BrowseFragment {


    private static final String TAG = MainFragment.class.getSimpleName();

    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;

    @SuppressLint("StaticFieldLeak")
    private static PicassoBackgroundManager picassoBackgroundManager = null;
    String description = "";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        setupUIElements();

        loadRows();

        setupEventListeners();

        picassoBackgroundManager = new PicassoBackgroundManager(getActivity());
    }

    private void setupEventListeners() {
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            // each time the item is clicked, code inside here will be executed.
            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                getActivity().startActivity(intent);
            } else if (item instanceof String) {
                if (item == "ErrorFragment") {
                    Intent intent = new Intent(getActivity(), ErrorActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private static final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            // each time the item is selected, code inside here will be executed.
            if (item instanceof String) {
                // GridItemPresenter
                picassoBackgroundManager.updateBackgroundWithDelay("https://images8.alphacoders.com/548/548006.jpg");
            } else if (item instanceof Movie) {
                // CardPresenter
                picassoBackgroundManager.updateBackgroundWithDelay(((Movie) item).getCardImageUrl());
            }
        }
    }

    private void setupUIElements() {
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.lb_page_indicator_dot));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }

    private void loadRows() {
        ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        /* GridItemPresenter */
        HeaderItem gridItemPresenterHeader = new HeaderItem(0, "Proximamente");

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add("Peliculas");
        mRowsAdapter.add(new ListRow(gridItemPresenterHeader, gridRowAdapter));

        /* CardPresenter */
        HeaderItem cardPresenterHeader = new HeaderItem(1, "Animes");
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);

        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();

            if (i == 0) {
                movie.setCardImageUrl("https://www.latercera.com/resizer/iNx2Xe_tJaW-n6ULx3eR2sgaLk0=/900x600/smart/arc-anglerfish-arc2-prod-copesa.s3.amazonaws.com/public/TXCOZNRNOVEBXIGVTMU7YIMZSA.jpg");
                movie.setTitle("Boku no Hero Academia");
                movie.setStudio("Bones");
                description = "My Hero Academia es una serie de manga escrita e ilustrada por Kōhei Horikoshi. Se basa en un one-shot realizado por el mismo autor y publicado en el quinto volumen del manga Ōmagadoki Dōbutsuen bajo el nombre de My Hero.";

            } else if (i == 1) {
                movie.setCardImageUrl("https://areajugones.sport.es/wp-content/uploads/2021/11/shingeki-no-kyojin-animu-moar.webp");
                movie.setTitle("Shingeki No Kyojin");
                movie.setStudio("MAPPA");
                description = "Desde hace 100 años los titanes aparecieron de la nada y llevaron a la humanidad al borde de la extinción; la población vive encerrada en ciudades rodeadas de enormes muros. Eren Jaeger, un joven cansado del conformismo, sueña con el mundo exterior.";

            } else if (i == 2) {
                movie.setCardImageUrl("https://as01.epimg.net/meristation/imagenes/2022/05/26/noticias/1653557269_040579_1653557380_noticia_normal.jpg");
                movie.setTitle("Jujutsu Kaisen");
                movie.setStudio("MAPPA");
                description = "Jujutsu Kaisen, también conocida como Jujutsu Kaisen: Guerra de hechiceros en España, es una serie de manga japonés escrita e ilustrada por Gege Akutami.";
            } else if (i == 3) {
                movie.setCardImageUrl("https://phantom-marca.unidadeditorial.es/a5fba56137680d1dd455cded139aa9e6/resize/1320/f/jpg/assets/multimedia/imagenes/2022/06/18/16555758407619.jpg");
                movie.setTitle("Tokyo Revengers");
                movie.setStudio("LIDENFILMS");
                description = "Tokyo Revengers es una serie de manga escrita e ilustrada por Ken Wakui, publicada en el semanario Shūkan Shōnen Magazine de Kōdansha desde marzo de 2017. Una adaptación a película de acción en vivo fue lanzada en Japón en julio de 2021.";
            } else {
                movie.setCardImageUrl("https://laverdadnoticias.com/__export/1640732197146/sites/laverdad/img/2021/12/28/yuri_on_ice_aniversario_studio_mappa.jpg_905403148.jpg");
                movie.setTitle("Yuri on Ice!!!");
                movie.setStudio("MAPPA");
                description = "Yuri!!! on Ice es una serie de anime con base y ambientación en el patinaje artístico sobre hielo. Fue producida por el estudio de animación MAPPA, dirigida por Sayo Yamamoto y escrita por Mitsurō Kubo.";
            }


            movie.setDescription(description);
            cardRowAdapter.add(movie);
        }

        mRowsAdapter.add(new ListRow(cardPresenterHeader, cardRowAdapter));

        /* Set */
        setAdapter(mRowsAdapter);
    }


    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {

        }
    }
}