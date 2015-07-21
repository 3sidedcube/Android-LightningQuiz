#Storm Library - Module Quiz

Storm is a collection of libraries that helps make mobile and desktop applications easy to create using a high quality WYSIACATWYG editor.

This module is an extension of UI that enables the use of quizes in the content.

#Usage

##Gradle

Simply include the following for your gradle dependencies `com.3sidedcube.storm:quiz:0.1a`.

**Note** The versioning of the library will always be as follows:

`Major version.Minor version.Bug fix`

It is safe to use `+` in part of of the `Bug fix` version, but do not trust it 100%. Always use a *specific* version to prevent regression errors.

##Code

In your application singleton, add the following code

```java
QuizSettings quizSettings = new QuizSettings.Builder(uiSettings).build();
```

The module requires a UiSettings object and has a direct dependency on LightningUi.

#Documentation

See the [Javadoc](http://3sidedcube.github.io/Android-LightningQuiz/) for full in-depth code-level documentation

#Contributors

[Callum Taylor (9A8BAD)](http://keybase.io/scruffyfox), [Tim Mathews (5C4869)](https://keybase.io/timxyz), [Matt Allen (DB74F5)](https://keybase.io/mallen), [Alan Le Fournis (067EA0)](https://keybase.io/alan3sc)

#License

See LICENSE.md
