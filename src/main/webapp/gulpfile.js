'use strict';

var gulp = require('gulp');

var gutil = require('gulp-util');
var runSequence = require('run-sequence');
var server = require('gulp-webserver');
var browserSync = require('browser-sync');
var browserSyncSpa = require('browser-sync-spa');

var $ = require('gulp-load-plugins')({
	pattern: ['gulp-*', 'del']
});

var paths = {
	src: './src/components',
	minify: './public/components',
	serving: './public'
};

var sourcesPatterns = {
	html: [paths.src+'/**/*.html'],
	js: [paths.src+'/**/*.js'],
	css: [paths.src+'/**/*.css'],
	mock: [paths.src+'/**/*.json']
};

gulp.task('default', ['no-minify', 'browser-sync']);

gulp.task('serve', function(){
	runSequence('minify', 'browser-sync');
});

gulp.task('watch', function () {
	gulp.watch([paths.src+'/**/*'], ['default']);
});

gulp.task('clean', function() {
	return $.del(paths.minify+'/**/*', {force: true});
});

gulp.task('minify', ['minify-html', 'minify-js', 'minify-css'], function () {
	return gulp.src(sourcesPatterns.mock)
	.pipe($.jsonminify())
	.pipe(gulp.dest(paths.minify))
	.pipe($.debug({title: 'minify'}));
});

gulp.task('no-minify', ['no-minify-html', 'no-minify-js', 'no-minify-css'], function() {
    return gulp.src(sourcesPatterns.mock)
        // .pipe($.jsonminify())
        .pipe(gulp.dest(paths.minify))
        .pipe($.debug({ title: 'no-minify' }));
});

gulp.task('minify-html', function() {
	return gulp.src(sourcesPatterns.html)
	.pipe($.minifyHtml({
		empty: true,
		spare: true,
		quotes: true
    }))
	.pipe(gulp.dest(paths.minify))
	.pipe($.debug({title: 'minify-html:'}));
});

gulp.task('minify-js', function () {
	return gulp.src(sourcesPatterns.js)
	.pipe($.ngAnnotate())
	.pipe($.uglify())
	.pipe(gulp.dest(paths.minify))
	.pipe($.debug({title: 'minify-js:'}));
});

gulp.task('minify-css', function () {
	return gulp.src(sourcesPatterns.css)
	.pipe($.csso())
	.pipe(gulp.dest(paths.minify))
	.pipe($.debug({title: 'minify-css:'}));
});

gulp.task('no-minify-html', function() {
    return gulp.src(sourcesPatterns.html)
        .pipe(gulp.dest(paths.minify))
        .pipe($.debug({ title: 'no-minify-html:' }));
});

gulp.task('no-minify-js', function() {
    return gulp.src(sourcesPatterns.js)
        .pipe(gulp.dest(paths.minify))
        .pipe($.debug({ title: 'no-minify-js:' }));
});

gulp.task('no-minify-css', function() {
    return gulp.src(sourcesPatterns.css)
        .pipe(gulp.dest(paths.minify))
        .pipe($.debug({ title: 'no-minify-css:' }));
});

gulp.task('server', function() {
	gulp.src(paths.serving)
	  .pipe(server({
		livereload: true,
		open: true,
		startPath:'/app',
		port: 3000
	}));
});
