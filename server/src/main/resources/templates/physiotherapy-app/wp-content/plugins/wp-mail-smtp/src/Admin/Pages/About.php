<?php

namespace WPMailSMTP\Admin\Pages;

use WPMailSMTP\Admin\Area;
use WPMailSMTP\Admin\PageAbstract;
use WPMailSMTP\Admin\PluginsInstallSkin;
use WPMailSMTP\Admin\PluginsInstallUpgrader;
use WPMailSMTP\WP;

/**
 * Class About to display a page with About Us and Versus content.
 *
 * @since 1.5.0
 */
class About extends PageAbstract {

	/**
	 * @since 1.5.0
	 *
	 * @var string Slug of a page.
	 */
	protected $slug = 'about';

	/**
	 * @since 1.5.0
	 *
	 * @var array List of supported tabs.
	 */
	protected $tabs = array( 'about', 'versus' );

	/**
	 * Get the page/tab link.
	 *
	 * @since 1.5.0
	 *
	 * @param string $tab Tab to generate a link to.
	 *
	 * @return string
	 */
	public function get_link( $tab = '' ) {

		return add_query_arg(
			'tab',
			$this->get_defined_tab( $tab ),
			WP::admin_url( 'admin.php?page=' . Area::SLUG . '-' . $this->slug )
		);
	}

	/**
	 * Get the current tab.
	 *
	 * @since 1.5.0
	 *
	 * @return string Current tab.
	 */
	public function get_current_tab() {

		if ( empty( $_GET['tab'] ) ) { // phpcs:ignore
			return $this->slug;
		}

		return $this->get_defined_tab( $_GET['tab'] ); // phpcs:ignore
	}

	/**
	 * Get the defined or default tab.
	 *
	 * @since 1.5.0
	 *
	 * @param string $tab Tab to check.
	 *
	 * @return string Defined tab. Fallback to default one if it doesn't exist.
	 */
	protected function get_defined_tab( $tab ) {

		$tab = \sanitize_key( $tab );

		return \in_array( $tab, $this->tabs, true ) ? $tab : $this->slug;
	}

	/**
	 * Get label for a tab.
	 * Process only those that exists.
	 * Defaults to "About Us".
	 *
	 * @since 1.5.0
	 *
	 * @param string $tab Tab to get label for.
	 *
	 * @return string
	 */
	public function get_label( $tab = '' ) {

		switch ( $this->get_defined_tab( $tab ) ) {
			case 'versus':
				$label = \sprintf(
					/* translators: %s - plugin current license type. */
					\esc_html__( '%s vs Pro', 'wp-mail-smtp' ),
					\ucfirst( \wp_mail_smtp()->get_license_type() )
				);
				break;

			case 'about':
			default:
				$label = \esc_html__( 'About Us', 'wp-mail-smtp' );
				break;
		}

		return $label;
	}

	/**
	 * @inheritdoc
	 */
	public function get_title() {
		return $this->get_label( $this->get_current_tab() );
	}

	/**
	 * Display About page content based on the current tab.
	 *
	 * @since 1.5.0
	 */
	public function display() {
		?>

		<div class="wp-mail-smtp-page-title">
			<a href="<?php echo \esc_url( $this->get_link() ); ?>" class="tab <?php echo $this->get_current_tab() === 'about' ? 'active' : ''; ?>">
				<?php echo \esc_html( $this->get_label( 'about' ) ); ?>
			</a>

			<?php if ( \wp_mail_smtp()->get_license_type() === 'lite' ) : ?>
				<a href="<?php echo \esc_url( $this->get_link( 'versus' ) ); ?>" class="tab <?php echo $this->get_current_tab() === 'versus' ? 'active' : ''; ?>">
					<?php echo \esc_html( $this->get_label( 'versus' ) ); ?>
				</a>
			<?php endif; ?>
		</div>

		<div class="wp-mail-smtp-page-content">
			<h1 class="screen-reader-text">
				<?php echo \esc_html( $this->get_label( $this->get_current_tab() ) ); ?>
			</h1>

			<?php
			$callback = 'display_' . $this->get_current_tab();

			if ( \method_exists( $this, $callback ) ) {
				$this->{$callback}();
			} else {
				$this->display_about();
			}
			?>
		</div>

		<?php
	}

	/**
	 * Display an "About Us" tab content.
	 *
	 * @since 1.5.0
	 */
	protected function display_about() {
		?>

		<div class="wp-mail-smtp-admin-about-section wp-mail-smtp-admin-columns">

			<div class="wp-mail-smtp-admin-column-60">
				<h3>
					<?php esc_html_e( 'Hello and welcome to WP Mail SMTP, the easiest and most popular WordPress SMTP plugin. We build software that helps your site reliably deliver emails every time.', 'wp-mail-smtp' ); ?>
				</h3>

				<p>
					<?php esc_html_e( 'Email deliverability has been a well-documented problem for all WordPress websites. However as WPForms grew, we became more aware of this painful issue that affects our users and the larger WordPress community. So we decided to solve this problem and make a solution that\'s beginner friendly.', 'wp-mail-smtp' ); ?>
				</p>
				<p>
					<?php esc_html_e( 'Our goal is to make reliable email deliverability easy for WordPress.', 'wp-mail-smtp' ); ?>
				</p>
				<p>
					<?php
					printf(
						wp_kses(
							/* translators: %1$s - WPForms URL, %2$s - WPBeginner URL, %3$s - OptinMonster URL, %4$s - MonsterInsights URL, %5$s - RafflePress URL */
							__( 'WP Mail SMTP is brought to you by the same team that\'s behind the most user friendly WordPress forms, <a href="%1$s" target="_blank" rel="noopener noreferrer">WPForms</a>, the largest WordPress resource site, <a href="%2$s" target="_blank" rel="noopener noreferrer">WPBeginner</a>, the most popular lead-generation software, <a href="%3$s" target="_blank" rel="noopener noreferrer">OptinMonster</a>, the best WordPress analytics plugin, <a href="%4$s" target="_blank" rel="noopener noreferrer">MonsterInsights</a>, and the most powerful WordPress contest plugin, <a href="%5$s" target="_blank" rel="noopener noreferrer">RafflePress</a>.', 'wp-mail-smtp' ),
							array(
								'a' => array(
									'href'   => array(),
									'rel'    => array(),
									'target' => array(),
								),
							)
						),
						'https://wpforms.com/?utm_source=wpmailsmtpplugin&utm_medium=pluginaboutpage&utm_campaign=aboutwpmailsmtp',
						'https://www.wpbeginner.com/?utm_source=wpmailsmtpplugin&utm_medium=pluginaboutpage&utm_campaign=aboutwpmailsmtp',
						'https://optinmonster.com/?utm_source=wpmailsmtpplugin&utm_medium=pluginaboutpage&utm_campaign=aboutwpmailsmtp',
						'https://www.monsterinsights.com/?utm_source=wpmailsmtpplugin&utm_medium=pluginaboutpage&utm_campaign=aboutwpmailsmtp',
						'https://rafflepress.com/?utm_source=wpmailsmtpplugin&utm_medium=pluginaboutpage&utm_campaign=aboutwpmailsmtp'
					);
					?>
				</p>
				<p>
					<?php esc_html_e( 'Yup, we know a thing or two about building awesome products that customers love.', 'wp-mail-smtp' ); ?>
				</p>
			</div>

			<div class="wp-mail-smtp-admin-column-40 wp-mail-smtp-admin-column-last">
				<figure>
					<img src="<?php echo esc_url( wp_mail_smtp()->assets_url . '/images/about/team.jpg' ); ?>" alt="<?php esc_attr_e( 'The WPForms Team photo', 'wp-mail-smtp' ); ?>">
					<figcaption>
						<?php esc_html_e( 'The WPForms Team', 'wp-mail-smtp' ); ?>
					</figcaption>
				</figure>
			</div>

		</div>

		<?php

		// Do not display the plugin section if the user can't install or activate them.
		if ( ! current_user_can( 'install_plugins' ) && ! current_user_can( 'activate_plugins' ) ) {
			return;
		}

		$this->display_plugins();
	}

	/**
	 * Display the plugins section.
	 *
	 * @since 2.2.0
	 */
	protected function display_plugins() {
		?>

		<div class="wp-mail-smtp-admin-about-plugins">
			<div class="plugins-container">
				<?php
				foreach ( $this->get_am_plugins() as $key => $plugin ) :
					$is_url_external = false;

					$data = $this->get_about_plugins_data( $plugin );

					if ( isset( $plugin['pro'] ) && \array_key_exists( $plugin['pro']['path'], \get_plugins() ) ) {
						$is_url_external = true;
						$plugin          = $plugin['pro'];

						$data = array_merge( $data, $this->get_about_plugins_data( $plugin, true ) );
					}

					// Do not display a plugin which has to be installed and the user can't install it.
					if ( ! current_user_can( 'install_plugins' ) && $data['status_class'] === 'status-download' ) {
						continue;
					}

					?>
					<div class="plugin-container">
						<div class="plugin-item">
							<div class="details wp-mail-smtp-clear">
								<img src="<?php echo \esc_url( $plugin['icon'] ); ?>" alt="<?php esc_attr_e( 'Plugin icon', 'wp-mail-smtp' ); ?>">
								<h5 class="plugin-name">
									<?php echo $plugin['name']; ?>
								</h5>
								<p class="plugin-desc">
									<?php echo $plugin['desc']; ?>
								</p>
							</div>
							<div class="actions wp-mail-smtp-clear">
								<div class="status">
									<strong>
										<?php
										\printf(
											/* translators: %s - status HTML text. */
											\esc_html__( 'Status: %s', 'wp-mail-smtp' ),
											'<span class="status-label ' . $data['status_class'] . '">' . $data['status_text'] . '</span>'
										);
										?>
									</strong>
								</div>
								<div class="action-button">
									<?php
									$go_to_class = '';
									if ( $is_url_external && $data['status_class'] === 'status-download' ) {
										$go_to_class = 'go_to';
									}
									?>
									<a href="<?php echo \esc_url( $plugin['url'] ); ?>"
										class="<?php echo \esc_attr( $data['action_class'] ); ?> <?php echo $go_to_class; ?>"
										data-plugin="<?php echo $data['plugin_src']; ?>">
										<?php echo $data['action_text']; ?>
									</a>
								</div>
							</div>
						</div>
					</div>
				<?php endforeach; ?>
			</div>
		</div>

		<?php
	}

	/**
	 * Generate all the required CSS classed and labels to be used in rendering.
	 *
	 * @since 1.5.0
	 *
	 * @param array $plugin
	 * @param bool  $is_pro
	 *
	 * @return mixed
	 */
	protected function get_about_plugins_data( $plugin, $is_pro = false ) {

		$data = array();

		if ( \array_key_exists( $plugin['path'], \get_plugins() ) ) {
			if ( \is_plugin_active( $plugin['path'] ) ) {
				// Status text/status.
				$data['status_class'] = 'status-active';
				$data['status_text']  = \esc_html__( 'Active', 'wp-mail-smtp' );
				// Button text/status.
				$data['action_class'] = $data['status_class'] . ' button button-secondary disabled';
				$data['action_text']  = \esc_html__( 'Activated', 'wp-mail-smtp' );
				$data['plugin_src']   = \esc_attr( $plugin['path'] );
			} else {
				// Status text/status.
				$data['status_class'] = 'status-inactive';
				$data['status_text']  = \esc_html__( 'Inactive', 'wp-mail-smtp' );
				// Button text/status.
				$data['action_class'] = $data['status_class'] . ' button button-secondary';
				$data['action_text']  = \esc_html__( 'Activate', 'wp-mail-smtp' );
				$data['plugin_src']   = \esc_attr( $plugin['path'] );
			}
		} else {
			if ( ! $is_pro ) {
				// Doesn't exist, install.
				// Status text/status.
				$data['status_class'] = 'status-download';
				$data['status_text']  = \esc_html__( 'Not Installed', 'wp-mail-smtp' );
				// Button text/status.
				$data['action_class'] = $data['status_class'] . ' button button-primary';
				$data['action_text']  = \esc_html__( 'Install Plugin', 'wp-mail-smtp' );
				$data['plugin_src']   = \esc_url( $plugin['url'] );
			}
		}

		return $data;
	}

	/**
	 * List of AM plugins that we propose to install.
	 *
	 * @since 1.5.0
	 *
	 * @return array
	 */
	private function get_am_plugins() {

		$data = array(
			'mi'          => array(
				'path' => 'google-analytics-for-wordpress/googleanalytics.php',
				'icon' => \wp_mail_smtp()->assets_url . '/images/about/plugin-mi.png',
				'name' => \esc_html__( 'MonsterInsights', 'wp-mail-smtp' ),
				'desc' => \esc_html__( 'MonsterInsights makes it “effortless” to properly connect your WordPress site with Google Analytics, so you can start making data-driven decisions to grow your business.', 'wp-mail-smtp' ),
				'url'  => 'https://downloads.wordpress.org/plugin/google-analytics-for-wordpress.zip',
				'pro'  => array(
					'path' => 'google-analytics-premium/googleanalytics-premium.php',
					'icon' => \wp_mail_smtp()->assets_url . '/images/about/plugin-mi.png',
					'name' => \esc_html__( 'MonsterInsights Pro', 'wp-mail-smtp' ),
					'desc' => \esc_html__( 'MonsterInsights makes it “effortless” to properly connect your WordPress site with Google Analytics, so you can start making data-driven decisions to grow your business.', 'wp-mail-smtp' ),
					'url'  => 'https://www.monsterinsights.com/?utm_source=WordPress&utm_medium=about&utm_campaign=smtp',
				),
			),
			'om'          => array(
				'path' => 'optinmonster/optin-monster-wp-api.php',
				'icon' => \wp_mail_smtp()->assets_url . '/images/about/plugin-om.png',
				'name' => \esc_html__( 'OptinMonster', 'wp-mail-smtp' ),
				'desc' => \esc_html__( 'Our high-converting optin forms like Exit-Intent® popups, Fullscreen Welcome Mats, and Scroll boxes help you dramatically boost conversions and get more email subscribers.', 'wp-mail-smtp' ),
				'url'  => 'https://downloads.wordpress.org/plugin/optinmonster.zip',
			),
			'wpforms'     => array(
				'path' => 'wpforms-lite/wpforms.php',
				'icon' => \wp_mail_smtp()->assets_url . '/images/about/plugin-wpf.png',
				'name' => \esc_html__( 'Contact Forms by WPForms', 'wp-mail-smtp' ),
				'desc' => \esc_html__( 'The best WordPress contact form plugin. Drag & Drop online form builder that helps you create beautiful contact forms with just a few clicks.', 'wp-mail-smtp' ),
				'url'  => 'https://downloads.wordpress.org/plugin/wpforms-lite.zip',
				'pro'  => array(
					'path' => 'wpforms/wpforms.php',
					'icon' => \wp_mail_smtp()->assets_url . '/images/about/plugin-wpf.png',
					'name' => \esc_html__( 'WPForms Pro', 'wp-mail-smtp' ),
					'desc' => \esc_html__( 'The best WordPress contact form plugin. Drag & Drop online form builder that helps you create beautiful contact forms with just a few clicks.', 'wp-mail-smtp' ),
					'url'  => 'https://wpforms.com/?utm_source=WordPress&utm_medium=about&utm_campaign=smtp',
				),
			),
			'rafflepress' => array(
				'path' => 'rafflepress/rafflepress.php',
				'icon' => \wp_mail_smtp()->assets_url . '/images/about/plugin-rp.png',
				'name' => \esc_html__( 'RafflePress', 'wp-mail-smtp' ),
				'desc' => \esc_html__( 'Turn your visitors into brand ambassadors! Easily grow your email list, website traffic, and social media followers with powerful viral giveaways & contests.', 'wp-mail-smtp' ),
				'url'  => 'https://downloads.wordpress.org/plugin/rafflepress.zip',
				'pro'  => array(
					'path' => 'rafflepress-pro/rafflepress-pro.php',
					'icon' => \wp_mail_smtp()->assets_url . '/images/about/plugin-rp.png',
					'name' => \esc_html__( 'RafflePress Pro', 'wp-mail-smtp' ),
					'desc' => \esc_html__( 'Turn your visitors into brand ambassadors! Easily grow your email list, website traffic, and social media followers with powerful viral giveaways & contests.', 'wp-mail-smtp' ),
					'url'  => 'https://rafflepress.com/pricing/',
				),
			),
		);

		return $data;
	}

	/**
	 * Active the given plugin.
	 *
	 * @since 1.5.0
	 */
	public static function ajax_plugin_activate() {

		// Run a security check.
		\check_ajax_referer( 'wp-mail-smtp-about', 'nonce' );

		$error = \esc_html__( 'Could not activate the plugin. Please activate it from the Plugins page.', 'wp-mail-smtp' );

		// Check for permissions.
		if ( ! \current_user_can( 'activate_plugins' ) ) {
			\wp_send_json_error( $error );
		}

		if ( isset( $_POST['plugin'] ) ) {

			$activate = \activate_plugins( $_POST['plugin'] ); // phpcs:ignore

			if ( ! \is_wp_error( $activate ) ) {
				\wp_send_json_success( esc_html__( 'Plugin activated.', 'wp-mail-smtp' ) );
			}
		}

		\wp_send_json_error( $error );
	}

	/**
	 * Install & activate the given plugin.
	 *
	 * @since 1.5.0
	 */
	public static function ajax_plugin_install() {

		// Run a security check.
		\check_ajax_referer( 'wp-mail-smtp-about', 'nonce' );

		$error = \esc_html__( 'Could not install the plugin.', 'wp-mail-smtp' );

		// Check for permissions.
		if ( ! \current_user_can( 'install_plugins' ) ) {
			\wp_send_json_error( $error );
		}

		if ( empty( $_POST['plugin'] ) ) {
			\wp_send_json_error();
		}

		// Set the current screen to avoid undefined notices.
		\set_current_screen( 'wp-mail-smtp_page_wp-mail-smtp-about' );

		// Prepare variables.
		$url = \esc_url_raw(
			\add_query_arg(
				array(
					'page' => 'wp-mail-smtp-about',
				),
				\admin_url( 'admin.php' )
			)
		);

		$creds = \request_filesystem_credentials( $url, '', false, false, null );

		// Check for file system permissions.
		if ( false === $creds ) {
			\wp_send_json_error( $error );
		}

		if ( ! \WP_Filesystem( $creds ) ) {
			\wp_send_json_error( $error );
		}

		// Do not allow WordPress to search/download translations, as this will break JS output.
		\remove_action( 'upgrader_process_complete', array( 'Language_Pack_Upgrader', 'async_upgrade' ), 20 );

		// Create the plugin upgrader with our custom skin.
		$installer = new PluginsInstallUpgrader( new PluginsInstallSkin() );

		// Error check.
		if ( ! \method_exists( $installer, 'install' ) || empty( $_POST['plugin'] ) ) {
			\wp_send_json_error( $error );
		}

		$installer->install( $_POST['plugin'] ); // phpcs:ignore

		// Flush the cache and return the newly installed plugin basename.
		\wp_cache_flush();

		if ( $installer->plugin_info() ) {

			$plugin_basename = $installer->plugin_info();

			// Activate the plugin silently.
			$activated = \activate_plugin( $plugin_basename );

			if ( ! \is_wp_error( $activated ) ) {
				\wp_send_json_success(
					array(
						'msg'          => \esc_html__( 'Plugin installed & activated.', 'wp-mail-smtp' ),
						'is_activated' => true,
						'basename'     => $plugin_basename,
					)
				);
			} else {
				\wp_send_json_success(
					array(
						'msg'          => esc_html__( 'Plugin installed.', 'wp-mail-smtp' ),
						'is_activated' => false,
						'basename'     => $plugin_basename,
					)
				);
			}
		}

		\wp_send_json_error( $error );
	}

	/**
	 * Display a "Lite vs Pro" tab content.
	 *
	 * @since 1.5.0
	 */
	protected function display_versus() {

		$license = \wp_mail_smtp()->get_license_type();
		?>

		<div class="wp-mail-smtp-admin-about-section wp-mail-smtp-admin-about-section-squashed">
			<h1 class="centered">
				<strong>
					<?php
					\printf(
						/* translators: %s - plugin current license type. */
						\esc_html__( '%s vs Pro', 'wp-mail-smtp' ),
						\esc_html( \ucfirst( $license ) )
					);
					?>
				</strong>
			</h1>

			<p class="centered <?php echo ( $license === 'pro' ? 'hidden' : '' ); ?>">
				<?php esc_html_e( 'Get the most out of WP Mail SMTP by upgrading to Pro and unlocking all of the powerful features.', 'wp-mail-smtp' ); ?>
			</p>
		</div>

		<div class="wp-mail-smtp-admin-about-section wp-mail-smtp-admin-about-section-squashed wp-mail-smtp-admin-about-section-hero wp-mail-smtp-admin-about-section-table">

			<div class="wp-mail-smtp-admin-about-section-hero-main wp-mail-smtp-admin-columns">
				<div class="wp-mail-smtp-admin-column-33">
					<h3 class="no-margin">
						<?php esc_html_e( 'Feature', 'wp-mail-smtp' ); ?>
					</h3>
				</div>
				<div class="wp-mail-smtp-admin-column-33">
					<h3 class="no-margin">
						<?php echo esc_html( ucfirst( $license ) ); ?>
					</h3>
				</div>
				<div class="wp-mail-smtp-admin-column-33">
					<h3 class="no-margin">
						<?php esc_html_e( 'Pro', 'wp-mail-smtp' ); ?>
					</h3>
				</div>
			</div>
			<div class="wp-mail-smtp-admin-about-section-hero-extra no-padding wp-mail-smtp-admin-columns">

				<table>
					<?php
					foreach ( $this->get_license_features() as $slug => $name ) {
						$current = $this->get_license_data( $slug, $license );
						$pro     = $this->get_license_data( $slug, 'pro' );
						?>
						<tr class="wp-mail-smtp-admin-columns">
							<td class="wp-mail-smtp-admin-column-33">
								<p><?php echo $name; ?></p>
							</td>
							<td class="wp-mail-smtp-admin-column-33">
								<p class="features-<?php echo esc_attr( $current['status'] ); ?>">
									<?php echo \implode( '<br>', $current['text'] ); ?>
								</p>
							</td>
							<td class="wp-mail-smtp-admin-column-33">
								<p class="features-full">
									<?php echo \implode( '<br>', $pro['text'] ); ?>
								</p>
							</td>
						</tr>
						<?php
					}
					?>
				</table>

			</div>

		</div>

		<?php if ( 'lite' === $license ) : ?>
			<div class="wp-mail-smtp-admin-about-section wp-mail-smtp-admin-about-section-hero">
				<div class="wp-mail-smtp-admin-about-section-hero-main no-border">
					<h3 class="call-to-action centered">
						<a href="<?php echo esc_url( wp_mail_smtp()->get_upgrade_link( 'lite-vs-pro' ) ); ?>" target="_blank" rel="noopener noreferrer">
							<?php \esc_html_e( 'Get WP Mail SMTP Pro Today and Unlock all of these Powerful Features', 'wp-mail-smtp' ); ?>
						</a>
					</h3>

					<p class="centered">
						<?php
						echo \wp_kses(
							\__( 'Bonus: WP Mail SMTP Lite users get <span class="price-off">$50 off regular price</span>, automatically applied at checkout.', 'wp-mail-smtp' ),
							array(
								'span' => array(
									'class' => array(),
								),
							)
						);
						?>
					</p>
				</div>
			</div>
		<?php endif; ?>

		<?php
	}

	/**
	 * Get the list of features for all licenses.
	 *
	 * @since 1.5.0
	 *
	 * @return array
	 */
	private function get_license_features() {

		return array(
			'log'     => \esc_html__( 'Email Log', 'wp-mail-smtp' ),
			'control' => \esc_html__( 'Email Controls', 'wp-mail-smtp' ),
			'mailers' => \esc_html__( 'Additional Mailers', 'wp-mail-smtp' ),
			'support' => \esc_html__( 'Customer Support', 'wp-mail-smtp' ),
		);
	}

	/**
	 * Get the array of data that compared the license data.
	 *
	 * @since 1.5.0
	 *
	 * @param string $feature Feature name.
	 * @param string $license License type to get data for.
	 *
	 * @return array|false
	 */
	private function get_license_data( $feature, $license ) {

		$data = array(
			'log'     => array(
				'lite' => array(
					'status' => 'none',
					'text'   => array(
						'<strong>' . esc_html__( 'Emails are not logged', 'wp-mail-smtp' ) . '</strong>',
					),
				),
				'pro'  => array(
					'status' => 'full',
					'text'   => array(
						'<strong>' . esc_html__( 'Complete Email Log management inside WordPress', 'wp-mail-smtp' ) . '</strong>',
					),
				),
			),
			'control' => array(
				'lite' => array(
					'status' => 'none',
					'text'   => array(
						'<strong>' . esc_html__( 'No controls over whether default WordPress emails are sent', 'wp-mail-smtp' ) . '</strong>',
					),
				),
				'pro'  => array(
					'status' => 'full',
					'text'   => array(
						'<strong>' . esc_html__( 'Complete Email Controls management for most default WordPress emails', 'wp-mail-smtp' ) . '</strong>',
					),
				),
			),
			'mailers' => array(
				'lite' => array(
					'status' => 'none',
					'text'   => array(
						'<strong>' . esc_html__( 'Only default list of mailers', 'wp-mail-smtp' ) . '</strong>',
					),
				),
				'pro'  => array(
					'status' => 'full',
					'text'   => array(
						'<strong>' . esc_html__( 'Additional mailers: Microsoft Outlook (with Office365 support) and Amazon SES', 'wp-mail-smtp' ) . '</strong>',
					),
				),
			),
			'support' => array(
				'lite' => array(
					'status' => 'none',
					'text'   => array(
						'<strong>' . esc_html__( 'Limited Support', 'wp-mail-smtp' ) . '</strong>',
					),
				),
				'pro'  => array(
					'status' => 'full',
					'text'   => array(
						'<strong>' . esc_html__( 'Priority Support', 'wp-mail-smtp' ) . '</strong>',
					),
				),
			),
		);

		// Wrong feature?
		if ( ! isset( $data[ $feature ] ) ) {
			return false;
		}

		// Wrong license type?
		if ( ! isset( $data[ $feature ][ $license ] ) ) {
			return false;
		}

		return $data[ $feature ][ $license ];
	}
}