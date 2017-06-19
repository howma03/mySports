import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {SiteHeaderComponent} from "./site-header/site-header.component";
import {AdminLinkDirective} from "./admin-link.directive";
import {TaskApiService} from "./task-api.service";
import {TaskSearchBoxComponent} from "./task-search-box/task-search-box.component";
import {ColorPickerComponent} from "./color-picker/color-picker.component";
import {ColorPreviewerComponent} from "./color-previewer/color-previewer.component";
import {ColorSampleComponent} from "./color-sample/color-sample.component";
import {HeroTableComponent} from "./hero-table/hero-table.component";
import {TaskListComponent} from "./task-list/task-list.component";
import {ShowOneTriggerDirective} from "./show-one-trigger.directive";
import {ShowOneContainerDirective} from "./show-one-container.directive";
import {ShowOneDirective} from "./show-one.directive";

@NgModule({
  declarations: [
    AppComponent,
    SiteHeaderComponent,
    AdminLinkDirective,
    TaskSearchBoxComponent,
    ColorPickerComponent,
    ColorPreviewerComponent,
    ColorSampleComponent,
    HeroTableComponent,
    TaskListComponent,
    ShowOneTriggerDirective,
    ShowOneContainerDirective,
    ShowOneDirective
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule
  ],
  providers: [TaskApiService],
  bootstrap: [AppComponent]
})
export class AppModule {


}
