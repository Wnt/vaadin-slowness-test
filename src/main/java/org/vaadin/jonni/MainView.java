package org.vaadin.jonni;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("")
@Theme(Lumo.class)
@HtmlImport("frontend://bower_components/vaadin-lumo-styles/presets/compact.html")
public class MainView extends VerticalLayout {

	private static final int COUNT = 16;

	public MainView() {
		Tabs mainTabs = new Tabs();
		add(mainTabs);
		mainTabs.setWidthFull();

		VerticalLayout firstTabContent = new VerticalLayout();
		firstTabContent.setSizeFull();
		VerticalLayout secondTabContent = new VerticalLayout();
		secondTabContent.setSizeFull();
		VerticalLayout thirdTabContent = new VerticalLayout();
		thirdTabContent.setSizeFull();
		VerticalLayout fourthTabContent = new VerticalLayout();
		fourthTabContent.setSizeFull();
		Tab firstTab = new Tab("Empty tab");
		Tab secondTab = new Tab("Grid");
		Tab thirdTab = new Tab("Datepickers in a layout");
		Tab fourthTab = new Tab("Datepickers in a Grid");
		mainTabs.add(firstTab, secondTab, thirdTab, fourthTab);

		mainTabs.addSelectedChangeListener(change -> {
			remove(firstTabContent);
			remove(secondTabContent);
			remove(thirdTabContent);
			remove(fourthTabContent);
			if (mainTabs.getSelectedTab() == firstTab) {
				add(firstTabContent);
				expand(firstTabContent);
			} else if (mainTabs.getSelectedTab() == secondTab) {
				add(secondTabContent);
				expand(secondTabContent);
			} else if (mainTabs.getSelectedTab() == thirdTab) {
				add(thirdTabContent);
				expand(thirdTabContent);
			} else {
				add(fourthTabContent);
				expand(fourthTabContent);
			}
		});

		Grid<Dto> grid = new Grid<>(Dto.class);
		List<Dto> data = new ArrayList<>();
		for (int i = 0; i < COUNT; i++) {
			data.add(getNewMockDto());
		}
		grid.setItems(data);
		grid.setSelectionMode(SelectionMode.MULTI);
		secondTabContent.add(grid);
		secondTabContent.expand(grid);

		for (int i = 0; i < COUNT; i++) {
			thirdTabContent.add(new DatePicker(LocalDate.now()));
		}
		thirdTabContent.setSpacing(false);

		Grid<Dto> datepickerGrid = new Grid<>(Dto.class);
		datepickerGrid.setItems(data);
		datepickerGrid.setSelectionMode(SelectionMode.MULTI);
		datepickerGrid.setColumns("property1");
		datepickerGrid.addComponentColumn(item -> {
			return new DatePicker(item.getDate().atZone(ZoneId.systemDefault()).toLocalDate());
		}).setHeader("Date");
		fourthTabContent.add(datepickerGrid);
		fourthTabContent.expand(datepickerGrid);

		setSizeFull();
	}

	private Dto getNewMockDto() {
		return new Dto(UUID.randomUUID().toString(), LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC));
	}

	public static class Dto {

		private String property1;
		private Instant date;

		public Dto(String property1, Instant date) {
			super();
			this.property1 = property1;
			this.date = date;
		}

		public String getProperty1() {
			return property1;
		}

		public void setProperty1(String property1) {
			this.property1 = property1;
		}

		public Instant getDate() {
			return date;
		}

		public void setDate(Instant date) {
			this.date = date;
		}

	}
}
